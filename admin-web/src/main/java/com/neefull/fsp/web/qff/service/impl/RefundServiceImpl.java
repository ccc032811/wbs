package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.qff.config.ProcessInstanceProperties;
import com.neefull.fsp.web.qff.entity.ProcessHistory;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.entity.Recent;
import com.neefull.fsp.web.qff.entity.Refund;
import com.neefull.fsp.web.qff.mapper.RefundMapper;
import com.neefull.fsp.web.qff.service.IDateImageService;
import com.neefull.fsp.web.qff.service.IRefundService;
import com.neefull.fsp.web.qff.utils.ProcessConstant;
import com.neefull.fsp.web.system.entity.User;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**退货QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  13:53
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RefundServiceImpl extends ServiceImpl<RefundMapper, Refund> implements IRefundService {


    private static final String FORM_NAME = "qff_refund";

    @Autowired
    private RefundMapper refundMapper;
    @Autowired
    private IDateImageService dateImageService;
    @Autowired
    private ProcessInstanceProperties properties;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Override
    public Integer addRefund(Refund refund){
        int count = refundMapper.insert(refund);
        return count;
    }

    @Override
    public Integer editRefund(Refund refund) {
        int count = refundMapper.updateById(refund);
        return count;
    }

    @Override
    public IPage<Refund> getRefundPage( Query query) {
        Page<Refund> page = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<Refund> pageInfo = refundMapper.getRefundPage(page,query);
        return pageInfo;
    }

    @Override
    public Integer updateRefundStatus(Integer id,Integer status) {
        Integer count = refundMapper.updateRefundStatus(id,status);
        return count;
    }

    @Override
    public Refund queryRefundById(Integer id) {
        Refund refund = refundMapper.selectById(id);
        return refund;
    }

//    @Override
//    @Transactional
//    public void commitProcess(Refund refund,User user) {
//        String format = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
//        refund.setRepTime(format);
//        editRefund(refund);
//        Map<String,Object > map = new HashMap<>();
//        map.put("user",user);
//        String businessKey = Refund.class.getSimpleName()+":"+refund.getId();
//        runtimeService.startProcessInstanceByKey(properties.getRefundProcess(),businessKey);
//        agreeCurrentProcess(refund,user);
//        updateRefundStatus(refund.getId(), ProcessConstant.UNDER_REVIEW);
//        addOrEditImage(refund,user);
//    }
//
//    @Override
//    @Transactional
//    public void agreeCurrentProcess(Refund refund, User user) {
//        Map<String,Object > map = new HashMap<>();
//        map.put("user",user);
//        //设置更新的时间
//        String format = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
//        refund.setRepTime(format);
//        editRefund(refund);
//        String businessKey = Refund.class.getSimpleName()+":"+refund.getId();
//        Task task = taskService.createTaskQuery().processDefinitionKey(properties.getRefundProcess()).processInstanceBusinessKey(businessKey).singleResult();
//        taskService.claim(task.getId(),user.getUsername());
//        taskService.complete(task.getId(),map);
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey, properties.getRefundProcess()).singleResult();
//        if(processInstance==null){
//            updateRefundStatus(refund.getId(),ProcessConstant.HAS_FINISHED);
//        }
//        addOrEditImage(refund,user);
//    }
//
//    @Override
//    public List<Refund> queryCurrentProcess(User user) {
//        List<Refund> list = new ArrayList<>();
//        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(properties.getRefundProcess()).taskCandidateOrAssigned(user.getUsername()).list();
//        if(CollectionUtils.isNotEmpty(tasks)){
//            for (Task task : tasks) {
//                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
//                Refund refund = queryRefundById(getId(processInstance.getBusinessKey()));
//                if(refund!=null){
//                    list.add(refund);
//                }
//            }
//        }
//        return list;
//    }
//
//    @Override
//    public List<String> getGroup(Refund refund) {
//        String businessKey = Refund.class.getSimpleName()+":"+refund.getId();
//        Task task = taskService.createTaskQuery().processDefinitionKey(properties.getRefundProcess()).processInstanceBusinessKey(businessKey).singleResult();
//        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
//        List<String> list = new ArrayList<>();
//        for (IdentityLink identityLink : identityLinksForTask) {
//            list.add(identityLink.getUserId());
//        }
//        return list;
//
//    }
//
//    @Override
//    @Transactional
//    public void addOrEditImage(Refund refund, User user) {
//
//        String image = dateImageService.queryImage(refund.getId(), user.getDeptName(), FORM_NAME);
//        if(StringUtils.isEmpty(image)){
//            dateImageService.insertDateImage(refund.getId(), user.getDeptName(), FORM_NAME,refund.getImages());
//        }else {
//            image= image+refund.getImages();
//            dateImageService.updateDateImage(refund.getId(), user.getDeptName(), FORM_NAME,image);
//        }
//
//    }
//
//    @Override
//    public List<ProcessHistory> queryHistory(Integer id) {
//        List<ProcessHistory> list = new ArrayList<>();
//        String businessKey = Refund.class.getSimpleName()+":"+id;
//        List<HistoricTaskInstance> taskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).processDefinitionKey(properties.getRefundProcess()).orderByHistoricTaskInstanceStartTime().asc().list();
//        for (HistoricTaskInstance taskInstance : taskInstances) {
//            ProcessHistory processHistory = new ProcessHistory();
//            processHistory.setName(taskInstance.getName());
//            if(taskInstance.getEndTime() ==null){
//                processHistory.setDate("");
//            }else {
//                processHistory.setDate(DateFormatUtils.format(taskInstance.getEndTime(),"yyyy-MM-dd"));
//            }
//
//            list.add(processHistory);
//        }
//        return list;
//
//    }
//
//    private Integer getId(String businessKey){
//        String starId = "";
//        if (businessKey.startsWith(Refund.class.getSimpleName())) {
//            if (StringUtils.isNotBlank(businessKey)) {
//                //截取字符串
//                starId = businessKey.split("\\:")[1].toString();
//            }
//        }
//        return Integer.parseInt(starId);
//    }


}
