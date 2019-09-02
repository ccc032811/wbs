package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.User;

/**
 * @author pei.wang
 */
public interface IUserService extends IService<User> {

    /**
     * 通过用户名查找用户
     *
     * @param user 用户ID
     * @return 用户
     */
    User findUserById(User user);

    /**
     * 通过手机号查找用户
     *
     * @param mobile 手机号
     * @return 用户
     */
    User findByMobile(String mobile);


    /**
     * 通过用户名查找用户详细信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    User findUserDetail(Long userId);


    /**
     * 新增用户
     *
     * @param user user
     */
    boolean createUser(User user);

    /**
     * 重置密码
     *
     * @param
     */
    boolean resetPassword(User user);

    /**
     * 验证登录
     *
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 更新用户信息
     * @return
     */
    int updateUser(User user);


}
