package com.neefull.fsp.web.sms.controller;

import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.sms.entity.Detail;
import com.neefull.fsp.web.sms.service.IDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:21
 */

@Slf4j
@RestController
@RequestMapping("/scan/smsDetail")
public class DetailController extends BaseController {

    @Autowired
    private IDetailService detailService;


    /**根据DN号查询对应的DN Detail集合
     * @param detail
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryDetailList")
    public FebsResponse queryDetailList(Detail detail) throws FebsException {
        try {
            List<Detail>  detailList = detailService.getDetailList(detail);
            Map<String, Object> data = new HashMap<>();
            data.put("rows", detailList);
            data.put("total", detailList.size());
            return new FebsResponse().success().data(data);
        } catch (Exception e) {
            String message = "查询DN Detail表失败！";
            log.error(message,e);
            throw new FebsException(message);
        }
    }




}
