package com.neefull.fsp.web.qff.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.qff.entity.Commodity;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.service.ICommodityService;
import com.neefull.fsp.web.system.entity.User;
import org.activiti.engine.task.IdentityLink;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *
 * @Author: chengchengchu
 * @Date: 2019/12/6  18:50
 */

@RestController
@RequestMapping("/commodity")
public class CommodityController extends BaseController {

    @Autowired
    private ICommodityService commodityService;


    /**新增养护QFF
     * @param commodity
     * @return
     * @throws FebsException
     */
    @PostMapping("/add")
    public FebsResponse addCommodity(Commodity commodity) throws FebsException {
        Integer count = commodityService.addCommodity(commodity);
        if(count!=1){
            throw new FebsException("新增到货养护包装QFF操作失败");
        }
        return new FebsResponse().success();
    }

    /**更新养护QFF
     * @param commodity
     * @return
     * @throws FebsException
     */
    @PostMapping("/edit")
    @RequiresPermissions("commodity:audit")
    public FebsResponse editCommodity(Commodity commodity) throws FebsException {
        Integer count = commodityService.editCommodity(commodity);
        if(count!=1){
            throw new FebsException("更新到货养护包装QFF操作失败");
        }
        return new FebsResponse().success();
    }

    /**获取养护QFF操作的所有信息
     * @param query
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("commodity:view")
    public FebsResponse getCommodityPage(Query query){
        IPage<Commodity> pageInfo = commodityService.getCommodityPage(query);
        Map<String, Object> dataTable = getDataTable(pageInfo);
        return new FebsResponse().success().data(dataTable);
    }

    /**删除养护QFF
     * @param id
     * @return
     */
    @GetMapping("/deleteCommodity/{id}")
    @RequiresPermissions("commodity:del")
    public FebsResponse updateCommodityStatus(@PathVariable Integer id) throws FebsException {
        int count = commodityService.updateCommodityStatus(id,4);
        if (count!=1){
            throw new FebsException("删除到货养护包装QFF操作失败");
        }
        return new FebsResponse().success();
    }

    /**根据编号查询
     * @param id
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryCommodity/{id}")
    public FebsResponse queryCommodityById(@PathVariable Integer id) throws FebsException {
        Commodity commodity = commodityService.queryCommodityById(id);
        if(commodity ==null){
            throw new FebsException("查询到货养护包装QFF操作失败");
        }
        return new FebsResponse().success().data(commodity);
    }

    /**提交流程
     * @param commodity
     * @return
     * @throws FebsException
     */
    @PostMapping("/commit")
    @RequiresPermissions("commodity:audit")
    public FebsResponse commitProcess(Commodity commodity) throws FebsException {
        User user = getCurrentUser();
        try {
            commodityService.commitProcess(commodity,user);
        } catch (Exception e) {
            throw new FebsException("提交申请失败");
        }
        return new FebsResponse().success();
    }

    /**同意当前任务
     * @param commodity
     * @return
     * @throws FebsException
     */
    @PostMapping("/agree")
    @RequiresPermissions("commodity:audit")
    public FebsResponse agreeCurrentProcess(Commodity commodity) throws FebsException {
        User user = getCurrentUser();
        List<String> group = commodityService.getGroupId(commodity,user);
        if(group.contains(user.getUsername())){
            try {
                commodityService.agreeCurrentProcess(commodity,user);
            } catch (Exception e) {
                throw new FebsException("同意流程失败");
            }
        }else {
            throw new FebsException("当前无权限或改数据已审核");
        }
        return new FebsResponse().success();
    }


    /**查询用户当前任务
     * @return
     * @throws FebsException
     */
    @GetMapping("/check")
    @RequiresPermissions("commodity:audit")
    public FebsResponse queryCurrentProcess() throws FebsException {
        User user = getCurrentUser();
        List<Commodity> list = commodityService.queryCurrentProcess(user);
        if(CollectionUtils.isEmpty(list)){
            throw new FebsException("没有任务");
        }
        return new FebsResponse().success().data(list);
    }




}
