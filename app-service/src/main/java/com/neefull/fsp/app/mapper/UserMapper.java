package com.neefull.fsp.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neefull.fsp.app.entity.User;
import com.neefull.fsp.app.entity.UserDetail;
import com.neefull.fsp.app.entity.UserResume;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author pei.wang
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     * @return 用户
     */
    User findByName(String username);

    /**
     * 通过手机号查找用户
     *
     * @param mobile 手机号
     * @return 用户
     */
    User findByMobile(String mobile);

    /**
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @return Ipage
     */
    IPage<User> findUserDetailPage(Page page, @Param("user") User user);

    /**
     * 查找用户详细信息
     *
     * @param user 用户对象，用于传递查询条件
     * @return List<User>
     */
    List<User> findUserDetail(@Param("user") User user);

    /**
     * 重置密码
     *
     * @param
     */
    @Update("update t_user set password=#{user.password} where mobile=#{user.mobile}")
    boolean forgetPassword(@Param("user") User user);

    /**
     * 重置密码
     *
     * @param
     */
    @Update("update t_user set password=#{user.password} where user_id=#{user.userId}")
    boolean resetPassword(@Param("user") User user);

    /**
     * 设置认证状态
     *
     * @param
     */
    @Update("update t_user set auth_status=#{user.authStatus},card_status=#{user.cardStatus} where user_id=#{user.userId}")
    int updateUserAuth(@Param("user") User user);

    /**
     * 更新用户名字和状态
     *
     * @param
     */
    @Update("update t_user set username=#{user.username},auth_status=#{user.authStatus},card_status=#{user.cardStatus} where user_id=#{user.userId}")
    int updateUserAuthWithName(@Param("user") User user);

    /**
     * 完成用户简历
     *
     * @param userResume
     * @return
     */
    int fillUserResume(@Param("userResume") UserResume userResume);

    /**
     * 查询用户简历
     *
     * @param userResume
     * @return
     */
    UserResume queryUserResume(@Param("userResume") UserResume userResume);

    /**
     * 查询用户详细信息
     *
     * @param userId
     * @return
     */
    UserDetail queryUserDetail(@Param("userId") long userId);

}
