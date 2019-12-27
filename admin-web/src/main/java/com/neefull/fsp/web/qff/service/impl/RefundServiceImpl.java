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
import com.neefull.fsp.web.qff.mapper.RefundMapper;
import com.neefull.fsp.web.qff.service.IRefundService;
import com.neefull.fsp.web.qff.utils.ProcessConstant;
import com.neefull.fsp.web.system.entity.User;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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

    @Autowired
    private RefundMapper refundMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

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

    @Override
    public void commitProcess(Refund refund,User user) {
        String format = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
        refund.setRepTime(format);
        editRefund(refund);
        Map<String,Object > map = new HashMap<>();
        map.put("user",user);
        String businessKey = Refund.class.getSimpleName()+":"+refund.getId();
        runtimeService.startProcessInstanceByKey("退货QFF",businessKey);

        updateRefundStatus(refund.getId(), ProcessConstant.UNDER_REVIEW);
    }

    @Override
    public void agreeCurrentProcess(Refund refund, User user) {
        Map<String,Object > map = new HashMap<>();
        map.put("user",user);
        //设置更新的时间
        String format = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
        refund.setRepTime(format);
        editRefund(refund);
        String businessKey = Refund.class.getSimpleName()+":"+refund.getId();
        Task task = taskService.createTaskQuery().processDefinitionKey("退货QFF").processInstanceBusinessKey(businessKey).singleResult();
        taskService.claim(task.getId(),user.getUsername());
        taskService.complete(task.getId(),map);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey, "退货QFF").singleResult();
        if(processInstance==null){
            updateRefundStatus(refund.getId(),ProcessConstant.HAS_FINISHED);
        }
    }

    @Override
    public List<Refund> queryCurrentProcess(User user) {
        List<Refund> list = new ArrayList<>();
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey("退货QFF").taskCandidateOrAssigned(user.getUsername()).list();
        if(CollectionUtils.isNotEmpty(tasks)){
            for (Task task : tasks) {
                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                Refund refund = queryRefundById(getId(processInstance.getBusinessKey()));
                if(refund!=null){
                    list.add(refund);
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getGroup(Refund refund) {
        String businessKey = Refund.class.getSimpleName()+":"+refund.getId();
        Task task = taskService.createTaskQuery().processDefinitionKey("退货QFF").processInstanceBusinessKey(businessKey).singleResult();
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
        List<String> list = new ArrayList<>();
        for (IdentityLink identityLink : identityLinksForTask) {
            list.add(identityLink.getGroupId());
        }
        return list;

    }

    private Integer getId(String businessKey){
        String starId = "";
        if (businessKey.startsWith(Refund.class.getSimpleName())) {
            if (StringUtils.isNotBlank(businessKey)) {
                //截取字符串
                starId = businessKey.split("\\:")[1].toString();
            }
        }
        return Integer.parseInt(starId);
    }


}
