package com.neefull.fsp.api.controller;

import com.neefull.common.core.entity.FebsResponse;
import com.neefull.common.core.oss.config.QiniuConfig;
import com.neefull.fsp.api.annotation.AuthToken;
import com.neefull.fsp.api.entity.AuthFreelancer;
import com.neefull.fsp.api.exception.BizException;
import com.neefull.fsp.api.service.IAuthFreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Slf4j
@Validated
@RestController
@RequestMapping("/oss")
public class OssController {
    @Autowired
    QiniuConfig qiniuConfig;
    @Autowired
    IAuthFreeService iAuthFreeService;

    /**
     * 客户端获取上传凭证
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/getUpToken", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String findByMobile(@RequestBody String params) {
        String token = qiniuConfig.getOssManager().getUpToken(qiniuConfig);
        return new FebsResponse().success().data(token).message("获取上传凭证成功").toJson();
    }

    /**
     * 上传完成回调，保存上传信息
     *
     * @return
     */
    @RequestMapping(value = "/uploadCallBack", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String uploadCallBack(@RequestBody AuthFreelancer AuthFreelancer, HttpServletRequest httpRequest) {

        long userId = (long) httpRequest.getAttribute("userId");
        AuthFreelancer.setUserId(userId);
        int result = iAuthFreeService.saveAuthFreelancer(AuthFreelancer);
        if (result > 0) {
            return new FebsResponse().success().data(result).message("保存完成").toJson();
        } else {
            return new FebsResponse().fail().data("").message("保存失败，请再次尝试").toJson();
        }
    }

    /**
     * 获取保存文件的读取地址信息
     *
     * @return
     */
    @RequestMapping(value = "/getUserImgUrls", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    //  @AuthToken
    public String getUserImgUrl(@RequestBody AuthFreelancer AuthFreelancer, HttpServletRequest httpRequest) throws BizException {
        /*long userId = (long) httpRequest.getAttribute("userId");
        AuthFreelancer.setUserId(userId);*/
        AuthFreelancer.setUserId(9);
        AuthFreelancer = iAuthFreeService.queryUserInfo(AuthFreelancer);
        ;
        AuthFreelancer = iAuthFreeService.queryUserInfo(AuthFreelancer);
        if (null == AuthFreelancer) {
            return new FebsResponse().fail().data("").message("未能查询到信息").toJson();
        }
        //处理字符串
        try {
            AuthFreelancer.setIdImage1(qiniuConfig.getOssManager().getDownUrl(qiniuConfig, AuthFreelancer.getIdImage1()));
            AuthFreelancer.setIdImage2(qiniuConfig.getOssManager().getDownUrl(qiniuConfig, AuthFreelancer.getIdImage2()));
            AuthFreelancer.setCardImage1(qiniuConfig.getOssManager().getDownUrl(qiniuConfig, AuthFreelancer.getCardImage1()));
            AuthFreelancer.setCardImage2(qiniuConfig.getOssManager().getDownUrl(qiniuConfig, AuthFreelancer.getCardImage2()));
        } catch (UnsupportedEncodingException e) {
            throw new BizException("查询图片异常");
        }
        //TODO 返回的字符串，有问题
        return new FebsResponse().success().data(AuthFreelancer).message("").toJson();

    }
}
