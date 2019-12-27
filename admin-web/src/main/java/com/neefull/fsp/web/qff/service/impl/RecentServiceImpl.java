package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.qff.entity.Commodity;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.entity.Recent;
import com.neefull.fsp.web.qff.mapper.RecentMapper;
import com.neefull.fsp.web.qff.service.IRecentService;
import com.neefull.fsp.web.qff.utils.ProcessConstant;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**近效期QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  11:32
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RecentServiceImpl extends ServiceImpl<RecentMapper, Recent> implements IRecentService {

    @Autowired
    private RecentMapper recentMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Override
    public Integer addRecent(Recent recent) {
        int count = recentMapper.insert(recent);
        return count;
    }

    @Override
    public Integer editRecent(Recent recent) {
        int count = recentMapper.updateById(recent);
        return count;
    }

    @Override
    public IPage<Recent> getRecentPage(Query query) {
        Page<Recent> page = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<Recent> pageInfo = recentMapper.getRecentPage(page,query);
        return pageInfo;
    }

    @Override
    public Integer updateRecentStatus(Integer id,Integer status) {
        Integer count = recentMapper.updateRecentStatus(id,status);
        return count;
    }

    @Override
    public Recent queryRecentById(Integer id) {
        Recent recent = recentMapper.selectById(id);
        return recent;
    }

    @Override
    public void commitProcess(Recent recent,User user) {
        editRecent(recent);
        Map<String,Object> variable = new HashMap<>();
        variable.put("user",user);
        String businessKey = Recent.class.getSimpleName()+":"+recent.getId();
        runtimeService.startProcessInstanceByKey("近效期QFF",businessKey,variable);
        updateRecentStatus(recent.getId(), ProcessConstant.UNDER_REVIEW);
    }

    @Override
    public void agreeCurrentProcess(Recent recent, User user) {
        Map<String,Object> variable = new HashMap<>();
        variable.put("user",user);
        editRecent(recent);
        String businessKey = Recent.class.getSimpleName()+":"+recent.getId();
        Task task = taskService.createTaskQuery().processDefinitionKey("近效期QFF").processInstanceBusinessKey(businessKey).singleResult();
        taskService.claim(task.getId(),user.getUsername());
        taskService.complete(task.getId(),variable);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey, "近效期QFF").singleResult();
        if(processInstance==null){
            updateRecentStatus(recent.getId(),ProcessConstant.HAS_FINISHED);
        }
    }

    @Override
    public List<Recent> queryCurrentProcess(User user) {
        List<Recent> list = new ArrayList<>();
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey("近效期QFF").taskCandidateOrAssigned(user.getUsername()).list();
        if(CollectionUtils.isNotEmpty(tasks)){
            for (Task task : tasks) {
                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                Recent recent = queryRecentById(getId(processInstance.getBusinessKey()));
                if(recent!=null){
                    list.add(recent);
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getGroup(Recent recent) {
        String businessKey = Recent.class.getSimpleName()+":"+recent.getId();
        Task task = taskService.createTaskQuery().processDefinitionKey("近效期QFF").processInstanceBusinessKey(businessKey).singleResult();
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
        List<String> list = new ArrayList<>();
        for (IdentityLink identityLink : identityLinksForTask) {
            list.add(identityLink.getGroupId());
        }
        return list;

    }

    private Integer getId(String businessKey){
        String starId = "";
        if (businessKey.startsWith(Recent.class.getSimpleName())) {
            if (StringUtils.isNotBlank(businessKey)) {
                //截取字符串
                starId = businessKey.split("\\:")[1].toString();
            }
        }
        return Integer.parseInt(starId);
    }
}
