package com.neefull.fsp.web.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.sms.utils.ScanComment;
import com.neefull.fsp.web.system.entity.Factory;
import com.neefull.fsp.web.system.entity.User;
import com.neefull.fsp.web.system.mapper.FactoryMapper;
import com.neefull.fsp.web.system.service.IFactoryService;
import com.neefull.fsp.web.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/3  11:01
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FactoryServiceImpl extends ServiceImpl<FactoryMapper, Factory> implements IFactoryService {


    @Override
    public IPage<Factory> getFactoryPage(Factory factory) {

        IPage<Factory> factoryIPage = new Page<>(factory.getPageNum(),factory.getPageSize());
        return this.baseMapper.getFactoryPage(factoryIPage,factory);
    }

    @Override
    public void addFactory(Factory factory) {
        this.baseMapper.insert(factory);
    }

    @Override
    public Factory queryFactoryById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public void updateFactory(Factory factory) {
        this.baseMapper.updateById(factory);
    }

    @Override
    public void deleteFactory(String ids) {
        this.baseMapper.deleteById(ids);
    }

    @Override
    public List<Factory> getFactory() {
        QueryWrapper<Factory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", ScanComment.STATUS_ONE);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Factory> getFactoryByIds(User user) {
        String[] idList = user.getFactoryId().split(StringPool.COMMA);
        return this.baseMapper.selectBatchIds(Arrays.asList(idList));
    }
}
