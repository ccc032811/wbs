package com.neefull.fsp.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.neefull.common.core.entity.FebsResponse;
import com.neefull.common.core.util.AuthUtils;
import com.neefull.common.core.util.EncryptUtil;
import com.neefull.common.core.util.HttpUtils;
import com.neefull.common.core.util.RegxCheckUtil;
import com.neefull.fsp.api.annotation.AuthToken;
import com.neefull.fsp.api.config.IdCardAuthConfig;
import com.neefull.fsp.api.config.ServConstants;
import com.neefull.fsp.api.entity.User;
import com.neefull.fsp.api.entity.UserInfo;
import com.neefull.fsp.api.exception.BizException;
import com.neefull.fsp.api.service.IUserInfoService;
import com.neefull.fsp.api.service.IUserService;
import com.neefull.fsp.api.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pei.wang
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IdCardAuthConfig idCardAuthConfig;

    @RequestMapping(value = "/findByMobile", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String findByMobile(@RequestBody User user) {

        if (null != userService.findByMobile(user.getMobile())) {
            return new FebsResponse().success().data(user.getMobile()).message("用户存在").toJson();
        } else {
            return new FebsResponse().fail().message("用户未注册").toJson();
        }

    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getUser(@RequestBody User user) {

        if (userService.createUser(user)) {
            return new FebsResponse().success().data(user.getUserId()).message("用户注册成功").toJson();
        } else {
            return new FebsResponse().fail().message("用户注册失败").toJson();
        }

    }

    /**
     * 重置密码
     *
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String resetPassword(@RequestBody User user, HttpServletRequest httpRequest) {
        long userId = (long) httpRequest.getAttribute("userId");
        user.setUserId(userId);
        if (userService.resetPassword(user)) {
            return new FebsResponse().success().data(user.getUserId()).message("密码更新成功").toJson();
        } else {
            return new FebsResponse().fail().message("更新密码失败").toJson();
        }

    }

    @RequestMapping(value = "/login/in", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String loginIn(@RequestBody User user) throws BizException {
        //password为空，则默认不是采用密码登录，那么用户名可以使手机号，可以是设置的用户名
        String userName = user.getUsername();
        String password = user.getPassword();
        //1.首先判断用户的登录方式
        if (StringUtils.isEmpty(user.getPassword())) {

            //手机登录
            if (RegxCheckUtil.isPhone(userName)) {
                //查询用户手机是否存在,不存在抛出异常，存在则登录成功，设置redis,默认登录不过期
                user = userService.findByMobile(userName);
            }

        } else {
            //用户名密码登录，必须要验证用户名和密码
            String passwrod = EncryptUtil.encrypt(password, ServConstants.AES_KEY);
            user.setPassword(password);
            user = userService.login(user);

        }
        if (null == user) {
            return new FebsResponse().fail().message("账号或者密码错误").data(null).toJson();
        }
        String token = AuthUtils.encryptPid(user.getUserId(), ServConstants.AES_KEY);
        JSONObject result = new JSONObject();

        if (redisUtil.set("login" + user.getUserId(), token)) {
            result.put("token", token);
            result.put("userType", user.getUserType());
            result.put("status", user.getStatus());
            return new FebsResponse().success().data(result).toJson();
        } else {
            return new FebsResponse().fail().message("登录失败，异常原因").data(null).toJson();
        }

    }

    /**
     * 用户退出登录
     *
     * @return
     * @throws BizException
     */
    @AuthToken
    @RequestMapping(value = "/login/out", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String loginOut(HttpServletRequest httpRequest) throws BizException {
        long userId = (long) httpRequest.getAttribute("userId");
        //退出登录，设置Redis保存的Token失效
        redisUtil.del("login" + userId);
        return new FebsResponse().success().message("退出成功").data(null).toJson();

    }


    /**
     * 用户其他信息更新
     *
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String updateUser(@RequestBody User user, HttpServletRequest httpRequest) {
        long userId = (long) httpRequest.getAttribute("userId");
        user.setUserId(userId);
        if (userService.updateById(user)) {
            return new FebsResponse().success().data(user.getUserId()).message("用户信息更新成功").toJson();
        } else {
            return new FebsResponse().fail().message("用户信息更新失败").toJson();
        }

    }

    /**
     * 完善用户信息
     *
     * @param userInfo
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/perfectingInformation", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String perfectingInformation(@RequestBody UserInfo userInfo, HttpServletRequest httpRequest) {
        long userId = (long) httpRequest.getAttribute("userId");
        userInfo.setUserId(userId);
        if (userInfoService.perfectingInformation(userInfo)) {
            return new FebsResponse().success().data(userId).message("用户信息更新成功").toJson();
        } else {
            return new FebsResponse().fail().message("用户信息更新失败").toJson();
        }

    }

    /**
     * 用户实名认证，异步调用
     *
     * @return
     */
    @RequestMapping(value = "/idCardAudit", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String idcardAudit(@RequestBody UserInfo userInfo, HttpServletRequest httpRequest) {
        String host = idCardAuthConfig.getHost();
        String path = idCardAuthConfig.getPath();
        String method = idCardAuthConfig.getMethod();
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + idCardAuthConfig.getAppCode());
        querys.put("idcard", userInfo.getCardNo());
        querys.put("name", userInfo.getRealName());
        HttpUtils.doGetAsyn(host, path, method, headers, querys, new HttpUtils.CallBack() {
            @Override
            public void onRequestComplete(HttpResponse response) throws IOException {
                //认证结果，更新数据库，发送通知到手机app
                JSONObject object = null;
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "UTF-8");
                    object = JSONObject.parseObject(result);
                    JSONObject showapi_res_body = (JSONObject) object.get("showapi_res_body");
                    String code = showapi_res_body.getString("code");
                    if ("0".equals(code)) {
                        //更新用户实名状态
                        long userId = (long) httpRequest.getAttribute("userId");
                        User user = new User();
                        user.setUserId(userId);
                        user.setAuthStatus(1);
                        if (userService.updateUser(user) > 0) {
                            //用户状态更新成功，给用户发送通知消息
                            log.debug("通知用户实名成功");
                        } else {
                            //用户状态更新失败，进入人工认证队列
                            log.debug("通知用户实名失败");
                        }
                    }

                } else {
                    //TODO
                    System.out.println("自动认证失败");
                }

            }


        });
        return new FebsResponse().success().message("用户实名认证申请成功").toJson();
    }
}

