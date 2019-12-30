package com.neefull.fsp.web.qff.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.entity.Recent;
import com.neefull.fsp.web.qff.service.IRecentService;
import com.neefull.fsp.web.qff.utils.ProcessConstant;
import com.neefull.fsp.web.system.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 近效期QFF操作
 *
 * @Author: chengchengchu
 * @Date: 2019/11/29  16:09
 */

@RestController
@RequestMapping("/recent")
public class RecentController extends BaseController {


    @Autowired
    private IRecentService recentService;

    /**新增近效期QFF
     * @param recent
     * @return
     * @throws FebsException
     */
    @PostMapping("/add")
    public FebsResponse addRecent(Recent recent) throws FebsException {
        Integer count = recentService.addRecent(recent);
        if (count!=1){
            throw new FebsException("新增近效期QFF失败");
        }
        return new FebsResponse().success();
    }

    /**更新近效期QFF
     * @param recent
     * @return
     * @throws FebsException
     */
    @PostMapping("/edit")
    @RequiresPermissions("recent:audit")
    public FebsResponse editRecent(Recent recent) throws FebsException {
        Integer count = recentService.editRecent(recent);
        if(count!=1){
            throw new FebsException("更新近效期QFF失败");
        }
        return new FebsResponse().success();
    }

    /**查询近效期QFF
     * @param query
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("recent:view")
    public FebsResponse getRecentPage(Query query){
        IPage<Recent> pageInfo = recentService.getRecentPage(query);
        Map<String, Object> dataTable = getDataTable(pageInfo);
        return new FebsResponse().success().data(dataTable);
    }

    /**删除近效期QFF
     * @param id
     * @return
     * @throws FebsException
     */
    @GetMapping("/deleteRecent/{id}")
    @RequiresPermissions("recent:del")
    public FebsResponse updateRecentStatus(@PathVariable Integer id) throws FebsException {
        Integer count = recentService.updateRecentStatus(id, ProcessConstant.HAVE_ABNORMAL);
        if(count!=1){
            throw new FebsException("删除近效期QFF失败");
        }
        return new FebsResponse().success();
    }

    /**查询近效期QFF
     * @param id
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryRecent")
    @RequiresPermissions("recent:view")
    public FebsResponse queryRecentById(@RequestParam("id")Integer id) throws FebsException {
        Recent recent = recentService.queryRecentById(id);
        if(recent==null){
            throw new FebsException("查询近效期QFF失败");
        }
        return new FebsResponse().success().data(recent);
    }

    /**提交流程
     * @param recent
     * @return
     * @throws FebsException
     */
    @PostMapping("/commit")
    @RequiresPermissions("recent:audit")
    public FebsResponse commitProcess(Recent recent) throws FebsException {
        User user = getCurrentUser();
        try {
            recentService.commitProcess(recent,user);
            recentService.addOrEditImage(recent,user);
        } catch (Exception e) {
            throw new FebsException("提交申请失败");
        }
        return new FebsResponse().success();
    }

    /**同意当前任务
     * @param recent
     * @return
     * @throws FebsException
     */
    @PostMapping("/agree")
    @RequiresPermissions("recent:audit")
    public FebsResponse agreeCurrentProcess(Recent recent) throws FebsException {
        User user = getCurrentUser();
        List<String> group = recentService.getGroup(recent);
        if(group.contains(user.getUsername())){
            try {
                recentService.agreeCurrentProcess(recent,user);
                recentService.addOrEditImage(recent,user);
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
    @RequiresPermissions("recent:audit")
    public FebsResponse queryCurrentProcess() throws FebsException {
        User user = getCurrentUser();
        List<Recent> list = recentService.queryCurrentProcess(user);
        if(CollectionUtils.isEmpty(list)){
            throw new FebsException("没有任务");
        }
        return new FebsResponse().success().data(list);
    }

}
