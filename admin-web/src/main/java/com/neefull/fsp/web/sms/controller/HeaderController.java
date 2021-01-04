package com.neefull.fsp.web.sms.controller;

import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.sms.entity.Header;
import com.neefull.fsp.web.sms.entity.vo.HeaderVo;
import com.neefull.fsp.web.sms.service.IHeaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:07
 */
@Slf4j
@RestController
@RequestMapping("/scan/smsHeader")
public class HeaderController extends BaseController {

    @Autowired
    private IHeaderService headerService;


    /**查询DN Header
     * @param header
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryHeaderList")
    public FebsResponse queryHeaderList(Header header) throws FebsException {
        try {
            Map<String, Object> dataTable = getDataTable(headerService.queryHeaderList(header));
            return new FebsResponse().success().data(dataTable);
        } catch (Exception e) {
            String message = "查询DN Header表失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }



    /**根据DN号查询
     * @param delivery
     * @return
     * @throws FebsException
     */
    @GetMapping("/sms/header/{delivery}")
    public FebsResponse queryHeaderByDelivery(@PathVariable String delivery) throws FebsException {
        try {
            Header header = headerService.queryHeaderByDelivery(delivery);
            return new FebsResponse().success().data(header);
        } catch (Exception e) {
            String message = "查询DN Header失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    /**查询已扫但是没审核和审核未通过的dn
     * @param plant
     * @return
     */
    @GetMapping("/queryScanDn/{plant}/{delivery}")
    public FebsResponse queryScanDn(@PathVariable String plant,@PathVariable String delivery) throws FebsException {
        try {
            HeaderVo headerVo = headerService.queryScanDn(plant,delivery);
            return new FebsResponse().success().data(headerVo);
        } catch (Exception e) {
            String message = "查询DN失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    /**查询三个状态的数量
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryScanNumber")
    public FebsResponse queryScanNumber(Header header) throws FebsException {
        try {
            Map<String,Integer> num = headerService.queryScanNumber(header);
            return new FebsResponse().success().data(num);
        } catch (Exception e) {
            String message = "查询DN失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }



    /**查询扫描对比
     * @param header
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryCompareList")
    public FebsResponse queryCompareList(Header header) throws FebsException {
        try {
            Map<String, Object> dataTable = getDataTable(headerService.queryCompareList(header));
            return new FebsResponse().success().data(dataTable);
        } catch (Exception e) {
            String message = "查询扫描对比表失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }



}
