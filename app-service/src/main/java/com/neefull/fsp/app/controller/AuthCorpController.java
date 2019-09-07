package com.neefull.fsp.app.controller;


import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.entity.AuthCorp;
import com.neefull.fsp.app.mapper.AuthCorpMapper;
import com.neefull.fsp.app.service.IAuthCorpService;
import com.neefull.fsp.common.config.QiniuConfig;
import com.neefull.fsp.common.entity.FebsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

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
    @Autowired
    AuthCorpMapper authCorpMapper;
    @Autowired
    QiniuConfig qiniuConfig;

    @RequestMapping(value = "/corpCertification", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String corpCertification(@RequestBody AuthCorp authCorp, HttpServletRequest httpServletRequest)  {
        long userId = (long) httpServletRequest.getAttribute("userId");
        authCorp.setUserId(userId);
        //TODO 默认认证通过，保存数据
        int result = authCorpService.saveAuthCorp(authCorp);
        if (result > 0) {
            return new FebsResponse().success().data(result).message("申请认证成功,请等待审核").toJson();

        } else {
            return new FebsResponse().fail().data(result).message("申请企业认证失败,请重新录入信息").toJson();

        }

    }

    /**
     * 获取企业提交的认证信息
     *
     * @param authCorp
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/getCorpAuthInfo", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String getCorpAuthInfo(@RequestBody AuthCorp authCorp, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
            long userId = (long) httpServletRequest.getAttribute("userId");
            // long userId = 9;
            authCorp.setUserId(userId);
            authCorp = authCorpService.queryCorpByUserId(authCorp);
            if (null == authCorp) {
                return new FebsResponse().fail().data("").message("企业尚未提交认证资料").toJson();
            } else {
                //生成图片地址
                if (StringUtils.isNotEmpty(authCorp.getBusinessLience())) {
                    String privateUrl = qiniuConfig.getOssManager().getDownUrl(qiniuConfig, authCorp.getBusinessLience());
                    authCorp.setBusinessLience(privateUrl);
                }
                return new FebsResponse().success().data(authCorp).message("").toJson();
            }

    }

    /**
     * 获取企业提交的认证信息
     *
     * @param authCorp
     * @param httpServletRequest
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/updateCorpAuthInfo", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String updateCorpAuthInfo(@RequestBody AuthCorp authCorp, HttpServletRequest httpServletRequest) {
            long userId = (long) httpServletRequest.getAttribute("userId");
            //long userId = 9;
            authCorp.setUserId(userId);
            if (authCorp.getAuthStatus().equals("1")) {
                authCorp.setAuthpassTime(new Date());
                authCorp.setAuthpassUser(authCorp.getAuthpassUser());
            }
            int result = authCorpService.updateAuthCorp(authCorp);
            if (result <= 0) {
                return new FebsResponse().fail().data("").message("提交修改失败").toJson();
            } else {
                return new FebsResponse().success().data(result).message("修改成功").toJson();
            }
    }
}
