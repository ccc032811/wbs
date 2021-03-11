package com.neefull.fsp.web.sms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.sms.entity.TmsData;
import com.neefull.fsp.web.sms.service.ITmsDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/9  13:18
 */

@Slf4j
@RestController
@RequestMapping("/tms")
public class TmsDataController extends BaseController {

    @Autowired
    private ITmsDataService tmsDataService;

    //对接TMS数据
    @GetMapping("/queryTmsList")
    public FebsResponse queryTmsList(TmsData tmsData) throws FebsException {
        try {
            IPage<TmsData> tmsDataIPage = tmsDataService.queryTmsList(tmsData);
            return new FebsResponse().success().data(getDataTable(tmsDataIPage));
        } catch (Exception e) {
            String message = "查询对接TMS记录失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }

    }


}
