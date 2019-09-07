package com.neefull.fsp.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.config.AppConstant;
import com.neefull.fsp.app.entity.User;
import com.neefull.fsp.app.service.IUserService;
import com.neefull.fsp.app.utils.RedisUtil;
import com.neefull.fsp.common.entity.FebsResponse;
import com.neefull.fsp.common.util.AuthUtils;
import com.neefull.fsp.common.util.EncryptUtil;
import com.neefull.fsp.common.util.RegxCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    private RedisUtil redisUtil;

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
     * 用户注册/登录
     *
     * @param params 用户参数
     * @return
     */
    @RequestMapping(value = "/loginAndregister", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String loginAndregister(@RequestBody User user) {
        //首先，根据手机号码查询用户是否存在，不存在则注册，存在查询返回用户信息
        String mobile = user.getMobile();
        user = userService.findByMobile(mobile);
        if (null == user) {
            user = new User();
            user.setAuthStatus(0);
            user.setCardStatus(0);
            user.setMobile(mobile);
            if (userService.createUser(user)) {
                //创建成功，可以直接登录
                user = userService.findByMobile(user.getMobile().trim());
            } else {
                return new FebsResponse().fail().data(user.getUserId()).message("发生网络故障，创建用户失败").toJson();
            }
        }
        //执行用户登录操作，返回用户信息和Token
        String token = loginToken(user.getUserId());
        if (null == token) {
            return new FebsResponse().fail().data(user.getUserId()).message("发生网络故障").toJson();
        } else {
            user.setToken(token);
            return new FebsResponse().success().data(user).message("").toJson();
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

    /**
     * 重置密码
     *
     * @return
     */
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String forgetPassword(@RequestBody User user) {
        log.debug("接收到的新密码是{}", user.getPassword());
        if (userService.forgetPassword(user)) {
            return new FebsResponse().success().data(user.getMobile()).message("密码更新成功").toJson();
        } else {
            return new FebsResponse().fail().data(null).message("更新密码失败").toJson();
        }

    }

    @RequestMapping(value = "/loginPassword", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String loginIn(@RequestBody User user) {
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
            password = EncryptUtil.encrypt(password, AppConstant.AES_KEY);
            user.setPassword(password);
            user = userService.login(user);

        }
        if (null == user) {
            return new FebsResponse().fail().message("账号或者密码错误").data(null).toJson();
        }
        String token = AuthUtils.encryptPid(user.getUserId(), AppConstant.AES_KEY);
        JSONObject result = new JSONObject();

        if (redisUtil.set("login" + user.getUserId(), token)) {
            user.setToken(token);
            return new FebsResponse().success().data(user).toJson();
        } else {
            return new FebsResponse().fail().message("登录失败，异常原因").data(null).toJson();
        }

    }

    /**
     * 用户退出登录
     *
     * @return
     */
    @AuthToken
    @RequestMapping(value = "/login/out", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String loginOut(HttpServletRequest httpRequest) {
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
        if (userService.updateUser(user) > 0) {
            user = userService.findUserById(user);
            return new FebsResponse().success().data(user).message("用户信息更新成功").toJson();
        } else {
            return new FebsResponse().fail().data(null).message("用户信息更新失败").toJson();
        }
    }

    /**
     * 用户其他信息更新
     *
     * @return
     */
    @RequestMapping(value = "/updateUserType", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String updateUserType(@RequestBody User user, HttpServletRequest httpRequest) {
        long userId = (long) httpRequest.getAttribute("userId");
        user.setUserId(userId);
        log.debug("userId:" + userId);
        String userTypeParams = user.getUserType();
        user = userService.findUserById(user);
        String userType = user.getUserType();
        //如果已经设置用户类型，则不允许更换
        if (null == userType || "".equals(userType)) {
            user.setUserType(userTypeParams);
            if (userService.updateUser(user) > 0) {
                return new FebsResponse().success().data(user).message("用户类型更新成功").toJson();
            } else {
                return new FebsResponse().fail().data(user).message("用户类型更新失败").toJson();
            }

        } else {
            return new FebsResponse().fail().data(user).message("用户已设置该信息").toJson();
        }
    }

    /**
     * 删除用户，测试用
     *
     * @return
     */
    @RequestMapping(value = "/deleteUserByMobile", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    // @AuthToken
    public String deleteUserByMobile(@RequestBody User user, HttpServletRequest httpRequest) {
        if (userService.deleteUserByMobile(user) > 0) {
            return new FebsResponse().success().data(user).message("删除成功").toJson();
        } else {
            return new FebsResponse().fail().data(user).message("删除失败").toJson();
        }
    }

    /**
     * 生成用户token
     *
     * @param userId
     * @return
     */
    private String loginToken(long userId) {
        String token = AuthUtils.encryptPid(userId, AppConstant.AES_KEY);
        JSONObject result = new JSONObject();
        if (redisUtil.set("login" + userId, token)) {
            return token;
        } else {
            return null;
        }
    }


}

