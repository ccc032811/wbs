package com.neefull.fsp.web.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.system.entity.Factory;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/3  11:01
 */
public interface FactoryMapper extends BaseMapper<Factory> {

    IPage<Factory> getFactoryPage(IPage<Factory> factoryIPage, Factory factory);
}
