package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.qff.entity.Refund;
import com.neefull.fsp.web.qff.mapper.RefundMapper;
import com.neefull.fsp.web.qff.service.IRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



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
    @Transactional
    public Integer addRefund(Refund refund){
        int count = refundMapper.insert(refund);
        return count;
    }

    @Override
    @Transactional
    public Integer editRefund(Refund refund) {
        int count = refundMapper.updateById(refund);
        return count;
    }

    @Override
    public IPage<Refund> getRefundPage(Refund refund) {
        Page<Refund> page = new Page<>(refund.getPageNum(),refund.getPageSize());
        IPage<Refund> pageInfo = refundMapper.getRefundPage(page,refund);
        return pageInfo;
    }

    @Override
    @Transactional
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
    public Refund queryRefundByNumber(String number) {
        QueryWrapper<Refund> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        return refundMapper.selectOne(queryWrapper);

    }


}
