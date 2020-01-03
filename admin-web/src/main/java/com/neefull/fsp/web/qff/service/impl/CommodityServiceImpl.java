package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.qff.config.ProcessInstanceProperties;
import com.neefull.fsp.web.qff.entity.Commodity;
import com.neefull.fsp.web.qff.entity.ProcessHistory;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.mapper.CommodityMapper;
import com.neefull.fsp.web.qff.mapper.DateImageMapper;
import com.neefull.fsp.web.qff.service.ICommodityService;
import com.neefull.fsp.web.qff.service.IDateImageService;
import com.neefull.fsp.web.qff.utils.ProcessConstant;
import com.neefull.fsp.web.system.entity.User;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.NativeUserQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/6  18:53
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {

    private static final String FORM_NAME = "qff_commodity";

    @Autowired
    private CommodityMapper commodityMapper;
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
    public Integer addCommodity(Commodity commodity) {
        int count = commodityMapper.insert(commodity);
        return count;
    }

    @Override
    public Integer editCommodity(Commodity commodity) {
        int count = commodityMapper.updateById(commodity);
        return count;
    }

    @Override
    public IPage<Commodity> getCommodityPage(Query query) {
        Page<Commodity> page = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<Commodity> pageInfo = commodityMapper.getConservePage(page,query);
        return pageInfo;
   }

    @Override
    public Integer updateCommodityStatus(Integer id,Integer status) {
        Integer count = commodityMapper.updateConserveStatus(id,status);
        return count;
    }

    @Override
    public Commodity queryCommodityById(Integer id) {
        Commodity commodity = commodityMapper.selectById(id);
        return commodity;
    }

//    @Override
//    @Transactional
//    public void commitProcess(Commodity commodity, User user) {
//
//        String format = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
//        commodity.setRepTime(format);
//        editCommodity(commodity);
//        Map<String,Object> variable = new HashMap<>();
//        variable.put("user",user);
//        String businessKey = Commodity.class.getSimpleName()+":"+commodity.getId();
//        //启动流程
//        runtimeService.startProcessInstanceByKey(properties.getCommodityProcess(), businessKey,variable);
//        agreeCurrentProcess(commodity,user);
//
//        //更改状态审核中
//        updateCommodityStatus(commodity.getId(), ProcessConstant.UNDER_REVIEW);
//        addOrEditImage(commodity,user);
//
//    }
//
//    @Override
//    @Transactional
//    public void agreeCurrentProcess(Commodity commodity, User user) {
//        Map<String,Object> variable = new HashMap<>();
//        variable.put("user",user);
//        //设置更新的时间
//        String format = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
//        commodity.setRepTime(format);
//        editCommodity(commodity);
//        String businessKey = Commodity.class.getSimpleName()+":"+commodity.getId();
//        Task task = taskService.createTaskQuery().processDefinitionKey(properties.getCommodityProcess()).processInstanceBusinessKey(businessKey).singleResult();
//        taskService.claim(task.getId(),user.getUsername());
//        taskService.complete(task.getId(),variable);
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey, properties.getCommodityProcess()).singleResult();
//        if(processInstance==null){
//            updateCommodityStatus(commodity.getId(),ProcessConstant.HAS_FINISHED);
//        }
//        addOrEditImage(commodity,user);
//    }
//
//    @Override
//    public List<Commodity> queryCurrentProcess(User user) {
//        List<Commodity> list = new ArrayList<>();
//        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(properties.getCommodityProcess()).taskCandidateOrAssigned(user.getUsername()).list();
//        if(CollectionUtils.isNotEmpty(tasks)){
//            for (Task task : tasks) {
//                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
//                Commodity commodity = queryCommodityById(getId(processInstance.getBusinessKey()));
//                if(commodity !=null){
//                    list.add(commodity);
//                }
//            }
//        }
//        return list;
//
//    }
//
//    @Override
//    public List<String> getGroupId(Commodity commodity, User user) {
//        String businessKey = Commodity.class.getSimpleName()+":"+commodity.getId();
//        Task task = taskService.createTaskQuery().processDefinitionKey(properties.getCommodityProcess()).processInstanceBusinessKey(businessKey).singleResult();
//        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
//        List<String> list = new ArrayList<>();
//        for (IdentityLink identityLink : identityLinksForTask) {
//            list.add(identityLink.getUserId());
//        }
//        return list;
//    }
//
//
//    @Override
//    @Transactional
//    public void addOrEditImage(Commodity commodity, User user) {
//        String queryImage = dateImageService.queryImage(commodity.getId(), user.getDeptName(), FORM_NAME);
//        if(StringUtils.isEmpty(queryImage)){
//            dateImageService.insertDateImage(commodity.getId(), user.getDeptName(),FORM_NAME,commodity.getImages());
//        }else {
//            queryImage = queryImage + commodity.getImages();
//            dateImageService.updateDateImage(commodity.getId(), user.getDeptName(),FORM_NAME,queryImage);
//        }
//    }
//
//    @Override
//    public List<ProcessHistory> queryHistory(Integer id) {
//        List<ProcessHistory> list = new ArrayList<>();
//        String businessKey = Commodity.class.getSimpleName()+":"+id;
//        List<HistoricTaskInstance> taskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).processDefinitionKey(properties.getCommodityProcess()).orderByHistoricTaskInstanceStartTime().asc().list();
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
//    }
//
//
//    private Integer getId(String businessKey){
//        String starId = "";
//        if (businessKey.startsWith(Commodity.class.getSimpleName())) {
//            if (StringUtils.isNotBlank(businessKey)) {
//                //截取字符串
//                starId = businessKey.split("\\:")[1].toString();
//            }
//        }
//        return Integer.parseInt(starId);
//    }
//
//
//    private Commodity queryCommodityByNumber(String businessKey) {
//        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("number", businessKey);
//        Commodity commodity = commodityMapper.selectOne(queryWrapper);
//        return commodity;
//    }


}
