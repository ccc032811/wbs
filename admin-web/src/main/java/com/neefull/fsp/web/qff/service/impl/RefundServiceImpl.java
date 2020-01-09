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


    @Autowired
    private RefundMapper refundMapper;

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


}
