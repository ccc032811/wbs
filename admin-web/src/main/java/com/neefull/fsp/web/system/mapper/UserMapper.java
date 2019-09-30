package com.neefull.fsp.web.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neefull.fsp.web.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @return Ipage
     */
    IPage<User> findUserDetailPage(Page page, @Param("user") User user);

    /**
     * 查找用户详细信息--系统用户
     *
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @return Ipage
     */
    IPage<User> findSysUserDetailPage(Page page, @Param("user") User user);

    /**
     * 查找用户详细信息
     *
     * @param user 用户对象，用于传递查询条件
     * @return List<User>
     */
    List<User> findUserDetail(@Param("user") User user);

    /**
     * 查找用户详细信息--系统用户
     *
     * @param user 用户对象，用于传递查询条件
     * @return List<User>
     */
    List<User> findSysUserDetail(@Param("user") User user);

    /**
     * 根据用户id更新用户实名状态
     * @param userId 用户Id
     * @param authStatus 实名状态
     */
    void updateUserAuthStatus(@Param("userId") Long userId, @Param("authStatus") String authStatus);

    /**
     * 首页统计图-用户分布情况
     * @return 用户分布数据
     */
    List<Map<String, String>> getUserDistribution();

}
