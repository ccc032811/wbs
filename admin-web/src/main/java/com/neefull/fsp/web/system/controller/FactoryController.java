package com.neefull.fsp.web.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.system.entity.Factory;
import com.neefull.fsp.web.system.entity.User;
import com.neefull.fsp.web.system.service.IFactoryService;
import com.neefull.fsp.web.system.service.IUserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/3  10:58
 */

@Slf4j
@RestController
@RequestMapping("/scan/factory")
public class FactoryController extends BaseController {

    @Autowired
    private IFactoryService factoryService;
    @Autowired
    private IUserService userService;


    @GetMapping("/list")
    public FebsResponse getFactoryList(Factory factory) throws FebsException {
        try {
            IPage<Factory> factoryIPage = factoryService.getFactoryPage(factory);
            return new FebsResponse().success().data(getDataTable(factoryIPage));
        } catch (Exception e) {
            String message = "查询工厂信息失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    /**添加工厂
     * @param factory
     * @return
     */
    @PostMapping("/addFactory")
    public FebsResponse addFactory(Factory factory) throws FebsException {
        try {
            factoryService.addFactory(factory);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "添加工厂信息失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**更新工厂
     * @param factory
     * @return
     * @throws FebsException
     */
    @PostMapping("/updateFactory")
    public FebsResponse updateFactory(Factory factory) throws FebsException {
        try {
            factoryService.updateFactory(factory);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改工厂信息失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    /**删除工厂
     * @param ids
     * @return
     * @throws FebsException
     */
    @GetMapping("/deleteFactory/{ids}")
    public FebsResponse deleteFactory(@PathVariable String ids) throws FebsException {
        try {
            factoryService.deleteFactory(ids);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改工厂信息失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/getFactory")
    public FebsResponse getFactory() throws FebsException {
        try {
            List<Factory> factoryList = factoryService.getFactory();
            return new FebsResponse().success().data(factoryList);
        } catch (Exception e) {
            String message = "获取工厂信息失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @GetMapping("/getFactoryByIds/{id}")
    public FebsResponse getfactoryByIds(@PathVariable String id) throws FebsException {
        try {
            User user = userService.getById(id);
            List<Factory> factoryList = factoryService.getFactoryByIds(user);
            return new FebsResponse().success().data(factoryList);
        } catch (Exception e) {
            String message = "根据id获取工厂信息失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }




}
