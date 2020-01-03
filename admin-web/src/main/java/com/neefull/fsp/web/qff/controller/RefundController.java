package com.neefull.fsp.web.qff.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.qff.entity.ProcessHistory;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.entity.Refund;
import com.neefull.fsp.web.qff.service.IProcessService;
import com.neefull.fsp.web.qff.service.IRefundService;
import com.neefull.fsp.web.qff.utils.ProcessConstant;
import com.neefull.fsp.web.system.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 退货QFF操作
 *
 * @Author: chengchengchu
 * @Date: 2019/11/29  16:12
 */

@RestController
@RequestMapping("/refund")
public class RefundController extends BaseController {


    @Autowired
    private IRefundService refundService;
    @Autowired
    private IProcessService processService;

    /**新增退货QFF
     * @param refund
     * @return
     * @throws FebsException
     */
    @PostMapping("/add")
    public FebsResponse addRefund(Refund refund) throws FebsException {
        Integer count = refundService.addRefund(refund);
        if(count!=1){
            throw new FebsException("新增退货QFF失败");
        }
        return new FebsResponse().success();
    }

    /**更新退货QFF
     * @param refund
     * @return
     * @throws FebsException
     */
    @PostMapping("/edit")
    @RequiresPermissions("refund:audit")
    public FebsResponse editRefund(Refund refund) throws FebsException {
        Integer count = refundService.editRefund(refund);
        if(count!=1){
            throw new FebsException("更新退货QFF失败");
        }
        return new FebsResponse().success();
    }

    /**查询退货QFF
     * @param query
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("refund:view")
    public FebsResponse getRefundPage(Query query){
        IPage<Refund> pageInfo = refundService.getRefundPage(query);
        Map<String, Object> dataTable = getDataTable(pageInfo);
        return new FebsResponse().success().data(dataTable);
    }

    /**删除退货QFF
     * @param id
     * @return
     * @throws FebsException
     */
    @GetMapping("/deleteRefund/{id}")
    @RequiresPermissions("refund:del")
    public FebsResponse updateRefundStatus(@PathVariable Integer id) throws FebsException {
        Integer count = refundService.updateRefundStatus(id, ProcessConstant.HAVE_ABNORMAL);
        if(count!=1){
            throw new FebsException("删除退货QFF");
        }
        return new FebsResponse().success();
    }

    /**查询退货QFF
     * @param id
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryRefund")
    @RequiresPermissions("refund:view")
    public FebsResponse queryRefundById(Integer id) throws FebsException {
        Refund refund = refundService.queryRefundById(id);
        if(refund==null){
            throw new FebsException("查询退货QFF失败");
        }
        return new FebsResponse().success().data(refund);
    }

    /**查询流程
     * @param refund
     * @return
     */
    @GetMapping("/queryHistory")
    public FebsResponse queryHistory(Refund refund){
        List<ProcessHistory> list = processService.queryHistory(refund);
        return new FebsResponse().success().data(list);
    }


//    /**查询流程
//     * @param id
//     * @return
//     */
//    @GetMapping("/queryHistory/{id}")
//    public FebsResponse queryHistory(@PathVariable Integer id){
//        List<ProcessHistory> list = refundService.queryHistory(id);
//        return new FebsResponse().success().data(list);
//    }


    /**提交流程
     * @param refund
     * @return
     * @throws FebsException
     */
    @PostMapping("/commit")
    @RequiresPermissions("refund:audit")
    public FebsResponse commitProcess(Refund refund) throws FebsException {
        User user = getCurrentUser();
        try {
//            refundService.commitProcess(refund,user);
            processService.commitProcess(refund,user);

        } catch (Exception e) {
            throw new FebsException("提交申请失败");
        }
        return new FebsResponse().success();
    }

    /**同意当前任务
     * @param refund
     * @return
     * @throws FebsException
     */
    @PostMapping("/agree")
    @RequiresPermissions("refund:audit")
    public FebsResponse agreeCurrentProcess(Refund refund) throws FebsException {
        User user = getCurrentUser();
        List<String> group = processService.getGroupId(refund,user);
//        List<String> group = refundService.getGroup(refund);
        if(group.contains(user.getUsername())){
            try {
//                refundService.agreeCurrentProcess(refund,user);
                processService.agreeCurrentProcess(refund,user);
            } catch (Exception e) {
                throw new FebsException("同意流程失败");
            }
        }else {
            throw new FebsException("当前无权限或改数据已审核");
        }
        return new FebsResponse().success();
    }

//    /**查询用户当前任务
//     * @return
//     * @throws FebsException
//     */
//    @GetMapping("/check")
//    @RequiresPermissions("refund:audit")
//    public FebsResponse queryCurrentProcess() throws FebsException {
//        User user = getCurrentUser();
//        List<Refund> list = refundService.queryCurrentProcess(user);
//        if(CollectionUtils.isEmpty(list)){
//            throw new FebsException("没有任务");
//        }
//        return new FebsResponse().success().data(list);
//    }



}
