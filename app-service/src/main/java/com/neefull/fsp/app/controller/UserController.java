package com.neefull.fsp.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.config.AppConstant;
import com.neefull.fsp.app.config.FspState;
import com.neefull.fsp.app.entity.*;
import com.neefull.fsp.app.mapper.AuthFreeMapper;
import com.neefull.fsp.app.mapper.ProjectTeamMapper;
import com.neefull.fsp.app.service.ITaskAnnexService;
import com.neefull.fsp.app.service.IUserService;
import com.neefull.fsp.app.utils.RedisUtil;
import com.neefull.fsp.common.config.QiniuConfig;
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
import java.io.UnsupportedEncodingException;
import java.util.List;

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
     * 选择用户类型
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
     * 查询用户信息
     *
     * @return
     */
    @RequestMapping(value = "/queryUserInfo", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String queryUserInfo(@RequestBody User user, HttpServletRequest httpRequest) {
        long userId = (long) httpRequest.getAttribute("userId");
        // long userId = 9;
        user.setUserId(userId);
        user = userService.findUserById(user);
        if (null != user) {
            return new FebsResponse().success().data(user).message("查询用户信息成功").toJson();
        } else {
            return new FebsResponse().fail().data(null).message("未查询到该用户信息").toJson();
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

    /**
     * 查询用户详细信息，包括建立信息，认证信息
     *
     * @return
     */
    @RequestMapping(value = "/queryUserDetail", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String queryUserDetail(@RequestBody UserDetail userDetail, HttpServletRequest httpRequest) {
        long userId = userDetail.getUserId();
        userDetail = userService.queryUserDetail(userId);
        if (null != userDetail) {
            return new FebsResponse().success().data(userDetail).message("查询用户详细成功").toJson();
        } else {
            return new FebsResponse().success().data(null).message("查询用户详细失败").toJson();
        }

    }

    /**
     * 补充简历信息
     *
     * @return
     */
    @RequestMapping(value = "/fillUserResume", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String fillUserResume(@RequestBody UserResume userResume, HttpServletRequest httpRequest) {
        long userId = (long) httpRequest.getAttribute("userId");
        // long userId = 38;
        userResume.setUserId(userId);
        int result = userService.fillUserResume(userResume);
        if (result > 0) {
            return new FebsResponse().success().data(result).message("简历保存成功").toJson();
        } else {
            return new FebsResponse().fail().data(result).message("简历保存失败").toJson();
        }

    }

    /**
     * 查询用户简历
     *
     * @return
     */
    @RequestMapping(value = "/queryUserResume", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String queryUserResume(@RequestBody UserResume userResume, HttpServletRequest httpRequest) {
        userResume = userService.queryUserResume(userResume);
        if (null != userResume) {
            //计算简历完整度
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(JSONObject.toJSON(userResume)));
            float size = jsonObject.keySet().size() - 1;
            userResume.setIntegrity(size / 15);
            return new FebsResponse().success().data(userResume).message("查询用户简历信息成功").toJson();
        } else {
            return new FebsResponse().success().data(null).message("未查询到该用户信息").toJson();
        }

    }

    /**
     * 用户完成任务
     *
     * @return
     */
    @Autowired
    ITaskAnnexService taskAnnexService;

    @RequestMapping(value = "/completedTask", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String completedTask(@RequestBody TaskAnnex taskAnnex, HttpServletRequest httpRequest) {
        long userId = (long) httpRequest.getAttribute("userId");
        taskAnnex.setUserId(userId);
        //保存所有明细信息和任务附件主信息
        int result = taskAnnexService.completedTask(taskAnnex);
        if (result > 0) {
            return new FebsResponse().success().data(result).message("提交任务成功").toJson();
        } else {
            return new FebsResponse().fail().data(result).message("提交任务失败").toJson();
        }

    }

    /**
     * 查询任务附件信息，包括任务描述，任务图片
     *
     * @return
     */
    @Autowired
    QiniuConfig qiniuConfig;

    @RequestMapping(value = "/queryTaskAnnex", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String queryTaskAnnex(@RequestBody TaskAnnex taskAnnex, HttpServletRequest httpRequest) {
        //获取主键信息
        taskAnnex = taskAnnexService.queryTaskAnnex(taskAnnex);
        List<TaskAnnexDetail> taskAnnexDetails = taskAnnex.getTaskAnnexDetailList();
        if (taskAnnexDetails.size() > 0) {

            taskAnnexDetails.stream().forEach(o ->
            {
                try {
                    o.setAnnexAddress(qiniuConfig.getOssManager().getDownUrl(qiniuConfig, o.getAnnexAddress()));
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage());
                }
            });
        }
        return new FebsResponse().success().data(taskAnnex).message("查询成功").toJson();

    }

    /**
     * 我的团队信息信息查询
     *
     * @return
     */
    @Autowired
    ProjectTeamMapper projectTeamMapper;
    @Autowired
    AuthFreeMapper authFreeMapper;

    @RequestMapping(value = "/queryMyTeams", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String queryMyTeams(@RequestBody User user, HttpServletRequest httpRequest) {
        if (null == user.getUserType()) {
            return new FebsResponse().fail().data(null).message("用户类型参数不可为空").toJson();
        }
        List<ProjectTeam> projectTeamList = null;
        long userId = (long) httpRequest.getAttribute("userId");
        //long userId = user.getUserId();
        //根据用户类型和UserId，来查询相关项目团队信息
        int userTypeInt = Integer.valueOf(user.getUserType());
        if (FspState.USER_TYPE.CORP.TYPE() == userTypeInt) {
            projectTeamList = this.projectTeamMapper.corpTeams(userId);
        } else if (FspState.USER_TYPE.FREELENCER.TYPE() == userTypeInt) {
            projectTeamList = this.projectTeamMapper.freelencerTeams(userId);
        }

        if (projectTeamList.size() > 0) {
            projectTeamList.stream().forEach(o -> {
                o.setTeamUsers(this.authFreeMapper.queryTeamUsers(o.getProjectId()));
            });
        }
        return new FebsResponse().fail().data(projectTeamList).message("团队信息查询成功").toJsonNoNull();

    }

    /**
     * 用户更换手机号码
     *
     * @param user
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/replacePhoneNum", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String replacePhoneNum(@RequestBody User user, HttpServletRequest httpRequest) {
        long userId = (long) httpRequest.getAttribute("userId");
        user.setUserId(userId);
        if (userService.replacePhoneNum(user)) {
            return new FebsResponse().success().data("").message("更新成功").toJsonNoNull();
        } else {
            return new FebsResponse().fail().data("").message("更新失败").toJsonNoNull();
        }
    }


}

