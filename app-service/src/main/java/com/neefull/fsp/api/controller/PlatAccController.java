package com.neefull.fsp.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.neefull.common.core.entity.FebsResponse;
import com.neefull.fsp.api.entity.PlatAccount;
import com.neefull.fsp.api.exception.BizException;
import com.neefull.fsp.api.mapper.PlatAccMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 平台账户
 *
 * @author pei.wang
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/plat")
public class PlatAccController {
    @Autowired
    PlatAccMapper platAccountMapper;

    @RequestMapping(value = "/getDefaultPlatAccount", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    // @AuthToken
    public String getDefaultPlatAccount(@RequestBody JSONObject params) throws BizException {

        PlatAccount platAccount = platAccountMapper.getDefaultPlatAccount();
        if (null == platAccount) {
            return new FebsResponse().success().data("").message("未查询信息").toJsonNoNull();
        }

        return new FebsResponse().success().data(platAccount).message("查询成功").toJsonNoNull();

    }
}