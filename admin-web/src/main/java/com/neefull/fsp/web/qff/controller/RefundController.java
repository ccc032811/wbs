package com.neefull.fsp.web.qff.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.qff.aspect.Qff;
import com.neefull.fsp.web.qff.entity.ProcessHistory;
import com.neefull.fsp.web.qff.entity.Refund;
import com.neefull.fsp.web.qff.service.IProcessService;
import com.neefull.fsp.web.qff.service.IRefundService;
import com.neefull.fsp.web.qff.utils.ProcessConstant;
import com.neefull.fsp.web.system.entity.User;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 退货QFF操作
 *
 * @Author: chengchengchu
 * @Date: 2019/11/29  16:12
 */
@Slf4j
@Validated
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
    @Qff("新增退货QFF")
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
    @Qff("更新退货QFF")
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
     * @param refund
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("refund:view")
    public FebsResponse getRefundPage(Refund refund){
        IPage<Refund> pageInfo = refundService.getRefundPage(refund);
        Map<String, Object> dataTable = getDataTable(pageInfo);
        return new FebsResponse().success().data(dataTable);
    }

    /**删除退货QFF
     * @param id
     * @return
     * @throws FebsException
     */
    @Qff("删除退货QFF")
    @GetMapping("/deleteRefund/{id}")
    @RequiresPermissions("refund:del")
    public FebsResponse updateRefundStatus(@PathVariable Integer id) throws FebsException {
        Refund refund = new Refund();
        refund.setId(id);
        processService.deleteInstance(refund);
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

    /**提交流程
     * @param refund
     * @return
     * @throws FebsException
     */
    @Qff("提交退货QFF流程")
    @PostMapping("/commit")
    @RequiresPermissions("refund:audit")
    public FebsResponse commitProcess(Refund refund) throws FebsException {
        User user = getCurrentUser();
        try {
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
    @Qff("同意退货QFF任务")
    @PostMapping("/agree")
    @RequiresPermissions("refund:audit")
    public FebsResponse agreeCurrentProcess(Refund refund) throws FebsException {
        User user = getCurrentUser();
        List<String> group = processService.getGroupId(refund,user);
        if(group.contains(user.getUsername())){
            try {
                processService.agreeCurrentProcess(refund,user);
            } catch (Exception e) {
                throw new FebsException("同意流程失败");
            }
        }else {
            throw new FebsException("当前无权限或改数据已审核");
        }
        return new FebsResponse().success();
    }

    /**导出excel
     * @param refund
     * @param response
     */
    @GetMapping("excel")
    @RequiresPermissions("refund:down")
    public void download(Refund refund, HttpServletResponse response){
        IPage<Refund> refundPage = refundService.getRefundPage(refund);
        List<Refund> refundList = refundPage.getRecords();
        ExcelKit.$Export(Refund.class, response).downXlsx(refundList, false);
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
