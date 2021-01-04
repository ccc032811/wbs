package com.neefull.fsp.web.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.sms.entity.Detail;
import com.neefull.fsp.web.sms.mapper.DetailMapper;
import com.neefull.fsp.web.sms.service.IDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:23
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DetailServiceImpl extends ServiceImpl<DetailMapper, Detail> implements IDetailService {

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void insertDetail(Detail detail) {
        this.baseMapper.insert(detail);
    }


    @Override
    public Map<String, Object> getDetailList(Detail detail) {
        List<Detail> detailList = this.baseMapper.getDetailList(detail);
        Map<String, Object> data = new HashMap<>();
        data.put("rows", detailList);
        data.put("total", detailList.size());
        return data;
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
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void updateScanQuntity(Integer id, String quantity) {
        this.baseMapper.updateScanQuntity(id,quantity);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void updateErrorMsg(Integer id, String msg,String status) {
        this.baseMapper.updateupdateErrorMsg(id,msg,status);
    }


    @Override
    public List<Detail> queryDetailByDelivery(String delivery) {
        QueryWrapper<Detail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delivery",delivery);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void updateStatusByDelivery(String delivery, String status) {
        this.baseMapper.updateStatusByDelivery(delivery,status);
    }


}
