package com.neefull.fsp.web.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.system.entity.Factory;
import com.neefull.fsp.web.system.entity.User;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/3  10:59
 */
public interface IFactoryService extends IService<Factory> {


    IPage<Factory> getFactoryPage(Factory factory);

    void addFactory(Factory factory);

    Factory queryFactoryById(Integer id);

    void updateFactory(Factory factory);

    void deleteFactory(String ids);

    List<Factory> getFactory();

    List<Factory> getFactoryByIds(User user);
}
