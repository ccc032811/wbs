package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.qff.config.ProcessInstanceProperties;
import com.neefull.fsp.web.qff.entity.*;
import com.neefull.fsp.web.qff.mapper.RocheMapper;
import com.neefull.fsp.web.qff.service.IDateImageService;
import com.neefull.fsp.web.qff.service.IRocheService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**罗氏内部发起QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  13:13
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RocheServiceImpl extends ServiceImpl<RocheMapper, Roche> implements IRocheService {

    private static final String FORM_NAME = "qff_roche";

    @Autowired
    private RocheMapper rocheMapper;
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
    public Integer addRoche(Roche roche) {
        int count = rocheMapper.insert(roche);
        return count;
    }

    @Override
    public Integer editRoche(Roche roche) {
        UpdateWrapper<Roche> update = new UpdateWrapper<>();
        update.eq("number",roche.getNumber());
        int count = rocheMapper.update(roche, update);
        return count;
    }

    @Override
    public IPage<Roche> getRochePage(Query query) {
        Page<Roche> page = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<Roche> pageInfo = rocheMapper.getRochePage(page,query);
        return pageInfo;
    }

    @Override
    public Integer updateRocheStatus(Integer id,Integer status) {
        Integer count = rocheMapper.updateRocheStatus(id,status);
        return count;
    }

    @Override
    public Roche queryRocheById(Integer id) {
        Roche roche = rocheMapper.selectById(id);
        return roche;
    }

//    @Override
//    @Transactional
//    public void commitProcess(Roche roche,User user) {
//        //新增
//        addRoche(roche);
//        Map<String,Object> map = new HashMap<>();
//        map.put("user",user);
//        //启动流程
//        String businessKey = Roche.class.getSimpleName()+":"+roche.getId();
//        runtimeService.startProcessInstanceByKey(properties.getRocheProcess(), businessKey);
//        agreeCurrentProcess(roche,user);
//        //更改状态审核中
//        updateRocheStatus(roche.getId(), ProcessConstant.UNDER_REVIEW);
//        addOrEditImages(roche,user);
//    }
//
//    @Override
//    @Transactional
//    public void agreeCurrentProcess(Roche roche, User user) {
//        //更新
//        editRoche(roche);
//        Map<String,Object> map = new HashMap<>();
//        map.put("user",user);
//        //同意任务
//        String businessKey = Roche.class.getSimpleName()+":"+roche.getId();
//        Task task = taskService.createTaskQuery().processDefinitionKey(properties.getRocheProcess()).processInstanceBusinessKey(businessKey).singleResult();
//        taskService.claim(task.getId(),user.getUsername());
//        taskService.complete(task.getId());
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey, properties.getRocheProcess()).singleResult();
//        if(processInstance==null){
//            updateRocheStatus(roche.getId(),ProcessConstant.HAS_FINISHED);
//        }
//        addOrEditImages(roche,user);
//    }
//
//    @Override
//    public List<Roche> queryCurrentProcess(User user) {
//        List<Roche> list = new ArrayList<>();
//        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(properties.getRocheProcess()).taskAssignee(user.getUsername()).list();
//        if(CollectionUtils.isNotEmpty(tasks)){
//            for (Task task : tasks) {
//                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
//                Roche roche = queryRocheById(getId(processInstance.getBusinessKey()));
//                if(roche!=null){
//                    list.add(roche);
//                }
//            }
//        }
//        return list;
//    }
//
//    @Override
//    public List<String> getGroup(Roche roche) {
//        String businessKey = Roche.class.getSimpleName()+":"+roche.getId();
//        Task task = taskService.createTaskQuery().processDefinitionKey(properties.getRocheProcess()).processInstanceBusinessKey(businessKey).singleResult();
//        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
//        List<String> list = new ArrayList<>();
//        for (IdentityLink identityLink : identityLinksForTask) {
//            list.add(identityLink.getUserId());
//        }
//        return list;
//    }
//
//    @Override
//    @Transactional
//    public void addOrEditImages(Roche roche, User user) {
//
//        String image = dateImageService.queryImage(roche.getId(), user.getDeptName(), FORM_NAME);
//        if(StringUtils.isEmpty(image)){
//            dateImageService.insertDateImage(roche.getId(), user.getDeptName(), FORM_NAME,roche.getImages());
//        }else {
//            image= image+roche.getImages();
//            dateImageService.updateDateImage(roche.getId(), user.getDeptName(), FORM_NAME,image);
//        }
//    }
//
//    @Override
//    public List<ProcessHistory> queryHistory(Integer id) {
//        List<ProcessHistory> list = new ArrayList<>();
//        String businessKey = Roche.class.getSimpleName()+":"+id;
//        List<HistoricTaskInstance> taskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).processDefinitionKey(properties.getRocheProcess()).orderByHistoricTaskInstanceStartTime().asc().list();
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
//        if (businessKey.startsWith(Roche.class.getSimpleName())) {
//            if (StringUtils.isNotBlank(businessKey)) {
//                //截取字符串
//                starId = businessKey.split("\\:")[1].toString();
//            }
//        }
//        return Integer.parseInt(starId);
//    }


}
