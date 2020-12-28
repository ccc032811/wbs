package com.neefull.fsp.web.sms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.common.annotation.Log;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.sms.entity.*;
import com.neefull.fsp.web.sms.entity.vo.DetailScanVo;
import com.neefull.fsp.web.sms.entity.vo.DetailVo;
import com.neefull.fsp.web.sms.entity.vo.HeaderVo;
import com.neefull.fsp.web.sms.service.IScanLogService;
import com.neefull.fsp.web.sms.service.IScanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:29
 */

@Slf4j
@Controller
@RequestMapping("/scan/smsScan")
public class ScanController extends BaseController {


    @Autowired
    private IScanService scanService;
    @Autowired
    private IScanLogService scanLogService;


    /**根据DN号去soap查询对应的信息
     * @param delivery
     * @return
     * @throws FebsException
     */
    @Log("获取DN信息")
    @GetMapping("/getDnMessage/{delivery}")
    @ResponseBody
    public FebsResponse getDnMessageByDelivery(@PathVariable String delivery) throws FebsException {

        try {
            DetailScanVo dnMessage = scanLogService.getDnMessageByDelivery(delivery);
            return new FebsResponse().success().data(dnMessage);
        } catch (Exception e) {
            String message = "根据DN号查询失败！";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


    /**审核DN    返回是否通过  map
     * @param dns
     * @return
     * @throws FebsException
     */
    @Log("审核DN")
    @GetMapping("/auditDn/{dns}")
    @ResponseBody
    public FebsResponse auditDn(@PathVariable String dns) throws FebsException {
        try {
            List<HeaderVo> headerVos = scanService.auditDn(dns);
            return new FebsResponse().success().data(headerVos);
        } catch (Exception e) {
            String message = "审核DN失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }



    /**新增扫描记录信息
     * @return
     */
    @Log("新增扫描记录")
    @PostMapping("/insertScanMsg")
    @ResponseBody
    public FebsResponse insertScanMsg(ScanAdd scanAdd) throws FebsException {
        try {
            scanService.insertScanMsg(scanAdd);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "新增扫描记录信息失败！";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


    /**分页查询扫描记录
     * @param scan
     * @return
     * @throws FebsException`
     */
    @GetMapping("/getScanInfoList")
    @ResponseBody
    public FebsResponse getScanInfoList(Scan scan) throws FebsException {
        try {
            IPage<Scan> headerList = scanService.getScanInfoList(scan);
            return new FebsResponse().success().data(getDataTable(headerList));
        } catch (Exception e) {
            String message = "查询扫描记录信息分页失败！";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


    /**查询扫描记录信息
     * @param scan
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryScanDetailList")
    @ResponseBody
    public FebsResponse queryScanDetailList(Scan scan) throws FebsException {
        try {
            IPage<Scan> scanDetailList = scanService.queryScanDetailList(scan);
            return new FebsResponse().success().data(getDataTable(scanDetailList));
        } catch (Exception e) {
            String message = "查询扫描记录信息失败";
            log.error(message,e);
            throw new FebsException(message);
        }

    }



    /**查询DN获取记录集合
     * @param scanLog
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryScanLogList")
    @ResponseBody
    public FebsResponse queryScanLogList(ScanLog scanLog) throws FebsException {
        try {
            Map<String, Object> dataTable = getDataTable(scanLogService.queryScanLogList(scanLog));
            return new FebsResponse().success().data(dataTable);
        } catch (Exception e) {
            String message = "查询已获取DN数据失败！";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


    /**修改扫描信息
     * @return
     * @throws FebsException
     */
    @Log("修改扫描信息")
    @PostMapping("/editScanDetail")
    @ResponseBody
    public FebsResponse editScanDetail(ScanAdd scanAdd) throws FebsException {
        try {
            scanService.editScanDetail(scanAdd.getScanList());
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "查询已获取DN数据失败！";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


    /**删除扫描信息
     * @param delivery
     * @return
     * @throws FebsException
     */
    @Log("删除扫描信息")
    @GetMapping("/deleteScanDetail/{delivery}")
    @ResponseBody
    public FebsResponse deleteScanDetail(@PathVariable String delivery) throws FebsException {
        try {
            scanService.deleteScanDetail(delivery);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除已扫DN数据失败！";
            log.error(message,e);
            throw new FebsException(message);
        }
    }




}
