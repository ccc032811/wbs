package com.neefull.fsp.api.controller;

import com.neefull.common.core.entity.FebsResponse;
import com.neefull.fsp.api.entity.AuthCorp;
import com.neefull.fsp.api.exception.BizException;
import com.neefull.fsp.api.service.IAuthCorpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 平台账户
 *
 * @author pei.wang
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
public class AuthCorpController {
    @Autowired
    IAuthCorpService authCorpService;

    @RequestMapping(value = "/corpCertification", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    // @AuthToken
    public String corpCertification(@RequestBody AuthCorp authCorp, HttpServletRequest httpServletRequest) throws BizException {
        // long userId = (long) httpServletRequest.getAttribute("userId");
        authCorp.setUserId(9);
        //TODO 默认认证通过，保存数据
        int result = authCorpService.saveAuthCorp(authCorp);
        if (result > 0) {
            authCorp.setAuthStatus("1");
            return new FebsResponse().success().data(result).message("企业认证成功").toJson();

        } else {
            return new FebsResponse().fail().data(result).message("企业认证失败").toJson();

        }


    }
}
