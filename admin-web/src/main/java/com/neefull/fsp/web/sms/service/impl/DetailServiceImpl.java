package com.neefull.fsp.web.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.sms.entity.Detail;
import com.neefull.fsp.web.sms.mapper.DetailMapper;
import com.neefull.fsp.web.sms.service.IDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:23
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DetailServiceImpl extends ServiceImpl<DetailMapper, Detail> implements IDetailService {

    @Override
    @Transactional
    public void insertDetail(Detail detail) {
        this.baseMapper.insert(detail);
    }


    @Override
    public List<Detail> getDetailList(Detail detail) {
        return this.baseMapper.getDetailList(detail);
    }


    @Override
    public Detail getDetailById(Integer id) {
        return this.baseMapper.selectById(id);
    }


    @Override
    public List<Detail> selectScanDetail(String dn) {
        return this.baseMapper.selectScanDetail(dn);
    }


    @Override
    @Transactional
    public void updateScanQuntity(Integer id, String quantity) {
        this.baseMapper.updateScanQuntity(id,quantity);
    }


    @Override
    @Transactional
    public void updateErrorMsg(Integer id, String msg,String status) {
        this.baseMapper.updateupdateErrorMsg(id,msg,status);
    }


    @Override
    public List<Detail> queryDetailByDelivery(String delivery) {
        QueryWrapper<Detail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delivery",delivery);
        return this.baseMapper.selectList(queryWrapper);
    }


}
