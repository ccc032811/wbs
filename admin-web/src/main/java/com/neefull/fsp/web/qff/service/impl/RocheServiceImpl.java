package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.entity.Recent;
import com.neefull.fsp.web.qff.entity.Refund;
import com.neefull.fsp.web.qff.entity.Roche;
import com.neefull.fsp.web.qff.mapper.RocheMapper;
import com.neefull.fsp.web.qff.service.IRocheService;
import com.neefull.fsp.web.system.entity.User;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**罗氏内部发起QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  13:13
 */

@Service
public class RocheServiceImpl extends ServiceImpl<RocheMapper, Roche> implements IRocheService {

    @Autowired
    private RocheMapper rocheMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;


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

    @Override
    public void commitProcess(Roche roche,User user) {
        editRoche(roche);
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        //启动流程
        String businessKey = Roche.class.getSimpleName()+":"+roche.getId();
        runtimeService.startProcessInstanceByKey("罗氏内部发起QFF", businessKey);
        //更改状态审核中
        updateRocheStatus(roche.getId(),2);
    }

    @Override
    public void agreeCurrentProcess(Roche roche, User user) {
        //更新
        editRoche(roche);
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        //同意任务
        String businessKey = Roche.class.getSimpleName()+":"+roche.getId();
        Task task = taskService.createTaskQuery().processDefinitionKey("罗氏内部发起QFF").processInstanceBusinessKey(businessKey).singleResult();
        taskService.claim(task.getId(),user.getUsername());
        taskService.complete(task.getId());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey, "罗氏内部发起QFF").singleResult();
        if(processInstance==null){
            updateRocheStatus(roche.getId(),3);
        }
    }

    @Override
    public List<Roche> queryCurrentProcess(User user) {
        List<Roche> list = new ArrayList<>();
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey("罗氏内部发起QFF").taskAssignee(user.getUsername()).list();
        if(CollectionUtils.isNotEmpty(tasks)){
            for (Task task : tasks) {
                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                Roche roche = queryRocheById(getId(processInstance.getBusinessKey()));
                if(roche!=null){
                    list.add(roche);
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getGroup(Roche roche) {
        String businessKey = Roche.class.getSimpleName()+":"+roche.getId();
        Task task = taskService.createTaskQuery().processDefinitionKey("罗氏内部发起QFF").processInstanceBusinessKey(businessKey).singleResult();
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
        List<String> list = new ArrayList<>();
        for (IdentityLink identityLink : identityLinksForTask) {
            list.add(identityLink.getGroupId());
        }
        return list;
    }

    private Integer getId(String businessKey){
        String starId = "";
        if (businessKey.startsWith(Roche.class.getSimpleName())) {
            if (StringUtils.isNotBlank(businessKey)) {
                //截取字符串
                starId = businessKey.split("\\:")[1].toString();
            }
        }
        return Integer.parseInt(starId);
    }


}
