package com.neefull.fsp.web.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.neefull.fsp.web.common.annotation.Log;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.system.entity.ServeMenu;
import com.neefull.fsp.web.system.service.IMenuService;
import com.neefull.fsp.web.system.service.IServeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @Author: chengchengchu
 * @Date: 2020/2/10  14:27
 */
@Slf4j
@RestController
@RequestMapping("/serveMenu")
public class ServeController extends BaseController {

    @Autowired
    private IServeService serveService;


    /**获取所有的服务
     * @param serveMenu
     * @return
     */
    @GetMapping("/list")
    public FebsResponse getServeMenus(ServeMenu serveMenu){
        IPage<ServeMenu> pageInfo = serveService.getServeMenus(serveMenu);
        Map<String, Object> dataTable = getDataTable(pageInfo);
        return new FebsResponse().success().data(dataTable);
    }


    /**更新服务状态
     * @param serveMenu
     * @return
     */
    @Log("更新服务状态")
    @GetMapping("/updateStatus")
    public FebsResponse updateStatusById(ServeMenu serveMenu){
        serveService.updateStatusById(serveMenu);
        return new FebsResponse().success();
    }

    /**新增服务
     * @param serveMenu
     * @return
     */
    @Log("新增服务")
    @PostMapping("/add")
    public FebsResponse addServeMenu(ServeMenu serveMenu){
        serveService.addServeMenu(serveMenu);
        return new FebsResponse().success();
    }

    /**删除服务
     * @param serveMenuIds
     * @return
     */
    @GetMapping("/delete/{serveMenuIds}")
    @RequiresPermissions("manage:del")
    public FebsResponse deleteServeMenu(@NotBlank(message = "{required}") @PathVariable String serveMenuIds){
        String[] ids = serveMenuIds.split(StringPool.COMMA);
        serveService.deleteServeMenu(ids);
        return new FebsResponse().success();
    }

    /**根据名称查询
     * @param name
     * @return
     */
    @GetMapping("/getUrl/{name}")
    public FebsResponse getServeUrl(@NotBlank(message = "{required}") @PathVariable String name){
        ServeMenu serveMenu = serveService.queryServeByName(name);
        return new FebsResponse().success().data(serveMenu);
    }

    /**查询所有在线的服务
     * @return
     */
    @GetMapping("/queryServe")
    public FebsResponse queryServe(){
        List<ServeMenu> serveMenuList = serveService.queryServe();
        return new FebsResponse().success().data(serveMenuList);
    }

}
