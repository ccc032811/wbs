package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.qff.config.ProcessInstanceProperties;
import com.neefull.fsp.web.qff.entity.Commodity;
import com.neefull.fsp.web.qff.entity.ProcessHistory;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.mapper.CommodityMapper;
import com.neefull.fsp.web.qff.mapper.DateImageMapper;
import com.neefull.fsp.web.qff.service.ICommodityService;
import com.neefull.fsp.web.qff.service.IDateImageService;
import com.neefull.fsp.web.qff.utils.ProcessConstant;
import com.neefull.fsp.web.system.entity.User;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.NativeUserQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public Integer addCommodity(Commodity commodity) {
        commodity.setStatus(ProcessConstant.NEW_BUILD);
        int count = commodityMapper.insert(commodity);
        return count;
    }

    @Override
    public Integer editCommodity(Commodity commodity) {
        int count = commodityMapper.updateById(commodity);
        return count;
    }

    @Override
    public IPage<Commodity> getCommodityPage(Query query) {
        Page<Commodity> page = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<Commodity> pageInfo = commodityMapper.getConservePage(page,query);
        return pageInfo;
   }

    @Override
    public Integer updateCommodityStatus(Integer id,Integer status) {
        Integer count = commodityMapper.updateConserveStatus(id,status);
        return count;
    }

    @Override
    public Commodity queryCommodityById(Integer id) {
        Commodity commodity = commodityMapper.selectById(id);
        return commodity;
    }




}
