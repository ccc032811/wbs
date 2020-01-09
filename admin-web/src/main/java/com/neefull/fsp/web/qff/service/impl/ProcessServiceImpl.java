package com.neefull.fsp.web.qff.service.impl;

import com.neefull.fsp.web.qff.config.ProcessInstanceProperties;
import com.neefull.fsp.web.qff.entity.*;
import com.neefull.fsp.web.qff.service.*;
import com.neefull.fsp.web.qff.utils.ProcessConstant;
import com.neefull.fsp.web.system.entity.User;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: chengchengchu
 * @Date: 2020/1/2  14:55
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProcessServiceImpl implements IProcessService {


    @Autowired
    private ICommodityService commodityService;
    @Autowired
    private IRefundService refundService;
    @Autowired
    private IRecentService recentService;
    @Autowired
    private IRocheService rocheService;
    @Autowired
    private IDateImageService dateImageService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProcessInstanceProperties properties;

    @Override
    public void commitProcess(Object object, User user) {

        Map<String,Object> variable = new HashMap<>();
        variable.put("user",user);

        if(object instanceof Commodity){
            Commodity commodity = (Commodity) object;
            editCommodity(commodity);

            String businessKey = Commodity.class.getSimpleName()+":"+commodity.getId();
            //启动流程
            runtimeService.startProcessInstanceByKey(properties.getCommodityProcess(), businessKey,variable);
            agreeCurrentProcess(commodity,user);
            //更改状态审核中
            commodityService.updateCommodityStatus(commodity.getId(), ProcessConstant.UNDER_REVIEW);
        }else if(object instanceof Refund){
            Refund refund = (Refund) object;
            editRefund(refund);

            String businessKey = Refund.class.getSimpleName()+":"+refund.getId();
            runtimeService.startProcessInstanceByKey(properties.getRefundProcess(),businessKey);
            agreeCurrentProcess(refund,user);
            refundService.updateRefundStatus(refund.getId(), ProcessConstant.UNDER_REVIEW);
        }else if(object instanceof Recent){
            Recent recent = (Recent) object;
            recentService.editRecent(recent);

            String businessKey = Recent.class.getSimpleName()+":"+recent.getId();
            runtimeService.startProcessInstanceByKey(properties.getRecentProcess(),businessKey,variable);
            agreeCurrentProcess(recent,user);
            recentService.updateRecentStatus(recent.getId(), ProcessConstant.UNDER_REVIEW);
        }else if(object instanceof Roche){
            Roche roche = (Roche) object;
            rocheService.addRoche(roche);

            String businessKey = Roche.class.getSimpleName()+":"+roche.getId();
            runtimeService.startProcessInstanceByKey(properties.getRocheProcess(), businessKey);
            agreeCurrentProcess(roche,user);
            //更改状态审核中
            rocheService.updateRocheStatus(roche.getId(), ProcessConstant.UNDER_REVIEW);
        }
    }


    @Override
    public List<String> getGroupId(Object object, User user) {
        List<String> list = new ArrayList<>();
        Task task = null;
        if(object instanceof Commodity){
            Commodity commodity = (Commodity) object;
            String businessKey = Commodity.class.getSimpleName()+":"+commodity.getId();
            task = taskService.createTaskQuery().processDefinitionKey(properties.getCommodityProcess()).processInstanceBusinessKey(businessKey).singleResult();
        }else if(object instanceof Refund){
            Refund refund = (Refund) object;
            String businessKey = Refund.class.getSimpleName()+":"+refund.getId();
            task = taskService.createTaskQuery().processDefinitionKey(properties.getRefundProcess()).processInstanceBusinessKey(businessKey).singleResult();
        }else if(object instanceof Recent){
            Recent recent = (Recent) object;
            String businessKey = Recent.class.getSimpleName()+":"+recent.getId();
            task = taskService.createTaskQuery().processDefinitionKey(properties.getRecentProcess()).processInstanceBusinessKey(businessKey).singleResult();
        }else if (object instanceof Roche){
            Roche roche = (Roche) object;
            String businessKey = Roche.class.getSimpleName()+":"+roche.getId();
            task = taskService.createTaskQuery().processDefinitionKey(properties.getRocheProcess()).processInstanceBusinessKey(businessKey).singleResult();
        }
        if(StringUtils.isNotEmpty(task.getId())){
            List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
            for (IdentityLink identityLink : identityLinksForTask) {
                list.add(identityLink.getUserId());
            }
        }
        return list;
    }

    @Override
    public void agreeCurrentProcess(Object object, User user) {
        //设置更新的时间
        Map<String,Object> variable = new HashMap<>();
        variable.put("user",user);

        if(object instanceof Commodity){
            Commodity commodity = (Commodity) object;
            editCommodity(commodity);

            String businessKey = Commodity.class.getSimpleName()+":"+commodity.getId();
            Task task = taskService.createTaskQuery().processDefinitionKey(properties.getCommodityProcess()).processInstanceBusinessKey(businessKey).singleResult();
            taskService.claim(task.getId(),user.getUsername());
            taskService.complete(task.getId(),variable);
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey, properties.getCommodityProcess()).singleResult();
            if(processInstance==null){
                commodityService.updateCommodityStatus(commodity.getId(),ProcessConstant.HAS_FINISHED);
            }
            if(StringUtils.isNotEmpty(commodity.getImages())){
                addOrEditImages(commodity,user);
            }
        }else if(object instanceof Recent){
            Recent recent = (Recent) object;

            recentService.editRecent(recent);
            String businessKey = Recent.class.getSimpleName()+":"+recent.getId();
            Task task = taskService.createTaskQuery().processDefinitionKey(properties.getRecentProcess()).processInstanceBusinessKey(businessKey).singleResult();
            taskService.claim(task.getId(),user.getUsername());
            taskService.complete(task.getId(),variable);
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey, properties.getRecentProcess()).singleResult();
            if(processInstance==null){
                recentService.updateRecentStatus(recent.getId(),ProcessConstant.HAS_FINISHED);
            }
            if(StringUtils.isNotEmpty(recent.getImages())){
                addOrEditImages(recent,user);
            }
        }else if(object instanceof Refund){
            Refund refund = (Refund) object;
            editRefund(refund);

            String businessKey = Refund.class.getSimpleName()+":"+refund.getId();
            Task task = taskService.createTaskQuery().processDefinitionKey(properties.getRefundProcess()).processInstanceBusinessKey(businessKey).singleResult();
            taskService.claim(task.getId(),user.getUsername());
            taskService.complete(task.getId(),variable);
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey, properties.getRefundProcess()).singleResult();
            if(processInstance==null){
                refundService.updateRefundStatus(refund.getId(),ProcessConstant.HAS_FINISHED);
            }
            if(StringUtils.isNotEmpty(refund.getImages())){
                addOrEditImages(refund,user);
            }
        }else if(object instanceof Roche){
            Roche roche = (Roche) object;
            rocheService.editRoche(roche);

            String businessKey = Roche.class.getSimpleName()+":"+roche.getId();
            Task task = taskService.createTaskQuery().processDefinitionKey(properties.getRocheProcess()).processInstanceBusinessKey(businessKey).singleResult();
            taskService.claim(task.getId(),user.getUsername());
            taskService.complete(task.getId(),variable);
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey, properties.getRocheProcess()).singleResult();
            if(processInstance==null){
                rocheService.updateRocheStatus(roche.getId(),ProcessConstant.HAS_FINISHED);
            }
            if(StringUtils.isNotEmpty(roche.getImages())){
                addOrEditImages(roche,user);
            }
        }
    }

    @Override
    public List<ProcessHistory> queryHistory(Object object) {
        List<ProcessHistory> list = new ArrayList<>();
        List<HistoricTaskInstance> taskInstances = null;
        if(object instanceof Commodity){
            Commodity commodity = (Commodity) object;
            String businessKey = Commodity.class.getSimpleName()+":"+commodity.getId();
            taskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).processDefinitionKey(properties.getCommodityProcess()).orderByHistoricTaskInstanceStartTime().asc().list();

        }else if(object instanceof Recent){
            Recent recent = (Recent) object;
            String businessKey = Recent.class.getSimpleName()+":"+recent.getId();
            taskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).processDefinitionKey(properties.getRecentProcess()).orderByHistoricTaskInstanceStartTime().asc().list();

        }else if(object instanceof Refund){
            Refund refund = (Refund) object;
            String businessKey = Refund.class.getSimpleName()+":"+refund.getId();
            taskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).processDefinitionKey(properties.getRefundProcess()).orderByHistoricTaskInstanceStartTime().asc().list();

        }else if(object instanceof Roche){
            Roche roche = (Roche) object;
            String businessKey = Roche.class.getSimpleName()+":"+roche.getId();
            taskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).processDefinitionKey(properties.getRocheProcess()).orderByHistoricTaskInstanceStartTime().asc().list();
        }
        if(taskInstances!=null){
            for (HistoricTaskInstance taskInstance : taskInstances) {
                ProcessHistory processHistory = new ProcessHistory();
                processHistory.setName(taskInstance.getName());
                if(taskInstance.getEndTime() ==null){
                    processHistory.setDate("");
                }else {
                    processHistory.setDate(DateFormatUtils.format(taskInstance.getEndTime(),"yyyy-MM-dd"));
                }

                list.add(processHistory);
            }
        }
        return list;
    }

    @Override
    public Integer findTask(String name) {

        List<Task> list = taskService.createTaskQuery().taskCandidateUser(name).list();
        return list.size();
    }


    private void addOrEditImages(Object object ,User user) {
        String image = "";

        if (object instanceof Commodity) {
            Commodity commodity = (Commodity) object;
            DateImage dateImage = dateImageService.queryImage(commodity.getId(), user.getDeptName(), ProcessConstant.COMMODITY_FORM);
            if (dateImage==null) {
                dateImageService.insertDateImage(commodity.getId(), user.getDeptName(), ProcessConstant.COMMODITY_FORM, commodity.getImages());
            } else {
                image = image + commodity.getImages();
                dateImageService.updateDateImage(commodity.getId(), user.getDeptName(), ProcessConstant.COMMODITY_FORM, image);
            }
        } else if (object instanceof Recent) {
            Recent recent = (Recent) object;
            DateImage dateImage = dateImageService.queryImage(recent.getId(), user.getDeptName(), ProcessConstant.RECENT_FORM);
            if (dateImage==null) {
                dateImageService.insertDateImage(recent.getId(), user.getDeptName(), ProcessConstant.RECENT_FORM, recent.getImages());
            } else {
                image = image + recent.getImages();
                dateImageService.updateDateImage(recent.getId(), user.getDeptName(), ProcessConstant.RECENT_FORM, image);
            }
        } else if (object instanceof Refund) {
            Refund refund = (Refund) object;
            DateImage dateImage = dateImageService.queryImage(refund.getId(), user.getDeptName(), ProcessConstant.REFUND_FORM);
            if (dateImage == null) {
                dateImageService.insertDateImage(refund.getId(), user.getDeptName(), ProcessConstant.REFUND_FORM, refund.getImages());
            } else {
                image = image + refund.getImages();
                dateImageService.updateDateImage(refund.getId(), user.getDeptName(), ProcessConstant.REFUND_FORM, image);
            }
        } else if (object instanceof Roche) {
            Roche roche = (Roche) object;
            DateImage dateImage = dateImageService.queryImage(roche.getId(), user.getDeptName(), ProcessConstant.ROCHE_FORM);
            if (dateImage==null) {
                dateImageService.insertDateImage(roche.getId(), user.getDeptName(), ProcessConstant.ROCHE_FORM, roche.getImages());
            } else {
                image = image + roche.getImages();
                dateImageService.updateDateImage(roche.getId(), user.getDeptName(), ProcessConstant.ROCHE_FORM, image);
            }
        }
    }

    private void editCommodity(Commodity commodity){
        String format = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
        commodity.setRepTime(format);
        commodityService.editCommodity(commodity);
    }

    private void editRefund(Refund refund){
        String format = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
        refund.setRepTime(format);
        refundService.editRefund(refund);
    }


}
