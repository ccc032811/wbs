package com.neefull.fsp.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.exception.BizException;
import com.neefull.fsp.app.service.IAuthFreeService;
import com.neefull.fsp.common.config.QiniuConfig;
import com.neefull.fsp.common.entity.FebsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String getUpToken(@RequestBody String params) {
        String token = qiniuConfig.getOssManager().getUpToken(qiniuConfig);
        return new FebsResponse().success().data(token).message("获取上传凭证成功").toJson();
    }

    /**
     * 获取Oss文件链接
     *
     * @param key oss上传成功后的Key
     * @return
     */
    @RequestMapping(value = "/getFileDownUrl", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String getFileDownUrl(@RequestBody JSONObject params) {
        try {
            String url = qiniuConfig.getOssManager().getDownUrl(qiniuConfig, params.getString("fileName"));
            if (StringUtils.isNotEmpty(url)) {
                return new FebsResponse().success().data(url).message("获取下载链接成功").toJson();
            }

        } catch (UnsupportedEncodingException e) {
            new BizException("链接Oss网络故障");
        }
        return new FebsResponse().fail().data(null).message("未查询到指定文件").toJson();
    }
}
