package com.neefull.fsp.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.neefull.common.core.entity.FebsResponse;
import com.neefull.fsp.api.config.AppConstant;
import com.neefull.fsp.api.entity.Sms;
import com.neefull.fsp.api.utils.RedisUtil;
import com.neefull.common.core.config.SmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RequestMapping("/sms")
@RestController
public class SmsController {

    private final Logger logger = LoggerFactory.getLogger(SmsController.class.getName());
    IAcsClient iAcsClient;
    @Autowired
    private SmsConfig smsConfig;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 发送短信验证码
     */
    @RequestMapping(value = "/sendSmsCode", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String sendMsg(@RequestBody Sms sms) throws Exception {
        CommonResponse response = null;
        String mobile = sms.getMobile();
        String smsType = sms.getSmsType();
        // 生成随机数
        String random = String.valueOf(new Random().nextInt(999999));
        String returnResult = "";
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(smsConfig.getDomain());
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.setConnectTimeout(smsConfig.getDefaultConnectTimeout());
        request.setReadTimeout(smsConfig.getDefaultReadTimeout());
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", smsConfig.getSignName());
        request.putQueryParameter("TemplateCode", AppConstant.SMS_TYPE.get(smsType));
        request.putQueryParameter("TemplateParam", "{\"code\":" + random + "}");
        try {

            iAcsClient = initSmsClient();
            response = iAcsClient.getCommonResponse(request);
            JSONObject result = JSONObject.parseObject(response.getData());
            //发送成功
            if (result.get("Code").equals("OK")) {
                // 保存随机数到redis,key是redis
                saveRandom(mobile, random);
                returnResult = new FebsResponse().success().message("验证码发送成功").data(random).toJson();
            } else {
                //发送失败
                saveRandom(mobile, random);
                returnResult = new FebsResponse().fail().message(result.getString("Message")).toJson();

            }
        } catch (ServerException e) {
            logger.error(e.getMessage());
        } catch (ClientException e) {
            logger.error(e.getMessage());
        }
        return returnResult;
    }

    /**
     * 校验短信验证码
     */
    @RequestMapping(value = "/checkSmsCode", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String checkSmsCode(@RequestBody Sms sms) {
        String key = "sms:" + sms.getMobile();
        if (sms.getCode().equals(redisUtil.get(key))) {
            return new FebsResponse().success().message("验证码校验通过").data(sms.getMobile()).toJson();
        } else {
            return new FebsResponse().fail().message("验证码校验失败").toJson();
        }
    }

    /**
     * 保存验证码到redis
     *
     * @param mobile
     * @param random
     */
    private void saveRandom(String mobile, String random) {
        String randomKey = "sms:" + mobile;
        // 5分钟失效
        redisUtil.set(randomKey, random,5 );
    }

    /**
     * 初始化短信发送客户端
     *
     * @return
     * @throws ClientException
     */
    private IAcsClient initSmsClient() throws ClientException {
        //初始化ascClient,暂时不支持多region
        DefaultProfile profile = DefaultProfile.getProfile("default", smsConfig.getAccessKeyId(), smsConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

}
