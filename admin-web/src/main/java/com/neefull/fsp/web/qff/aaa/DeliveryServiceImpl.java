/*package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.qff.aaa.Delivery;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.aaa.DeliveryMapper;
import com.neefull.fsp.web.qff.aaa.IDeliveryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

*//**到货QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  10:59
 *//*

@Service
public class DeliveryServiceImpl extends ServiceImpl<DeliveryMapper, Delivery> implements IDeliveryService {

    @Autowired
    private DeliveryMapper deliveryMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Override
    public Integer saveDelivery(Delivery delivery) {
        int count = deliveryMapper.insert(delivery);
        return count;
    }


    @Override
    public Integer editDelivery(Delivery delivery) {
        UpdateWrapper<Delivery> update = new UpdateWrapper<>();
        update.eq("number",delivery.getNumber());
        int count = deliveryMapper.update(delivery, update);
        return count;
    }

    @Override
    public IPage<Delivery> getDeliveryPage(Query query) {
        Page<Delivery> page = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<Delivery> pageInfo = deliveryMapper.getDeliveryPage(page,query);
        return pageInfo;

    }

    @Override
    public Integer deleteDelivery(String number) {
        Integer count = deliveryMapper.deleteDelivery(number,4);
        return count;
    }

    @Override
    public Delivery queryDeliveryByNumber(String number) {
        QueryWrapper<Delivery> query = new QueryWrapper<>();
        query.eq("number",number);
        Delivery delivery = deliveryMapper.selectOne(query);
        return delivery;
    }


}*/
