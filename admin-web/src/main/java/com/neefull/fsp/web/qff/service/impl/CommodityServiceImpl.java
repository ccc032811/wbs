package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.qff.entity.Commodity;
import com.neefull.fsp.web.qff.mapper.CommodityMapper;
import com.neefull.fsp.web.qff.service.ICommodityService;
import com.neefull.fsp.web.qff.utils.ProcessConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: chengchengchu
 * @Date: 2019/12/6  18:53
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {


    @Autowired
    private CommodityMapper commodityMapper;


    @Override
    @Transactional
    public Integer addCommodity(Commodity commodity) {
        commodity.setStatus(ProcessConstant.NEW_BUILD);
        int count = commodityMapper.insert(commodity);
        return count;
    }

    @Override
    @Transactional
    public Integer editCommodity(Commodity commodity) {
        int count = commodityMapper.updateById(commodity);
        return count;
    }

    @Override
    public IPage<Commodity> getCommodityPage(Commodity commodity) {
        Page<Commodity> page = new Page<>(commodity.getPageNum(),commodity.getPageSize());
        IPage<Commodity> pageInfo = commodityMapper.getConservePage(page,commodity);
        return pageInfo;
   }

    @Override
    @Transactional
    public Integer updateCommodityStatus(Integer id,Integer status) {
        Integer count = commodityMapper.updateConserveStatus(id,status);
        return count;
    }

    @Override
    public Commodity queryCommodityById(Integer id) {
        Commodity commodity = commodityMapper.selectById(id);
        return commodity;
    }

    @Override
    public Commodity queryCommodityByNumber(String number) {
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        return  commodityMapper.selectOne(queryWrapper);

    }


}
