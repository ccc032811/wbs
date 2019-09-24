package com.neefull.fsp.web.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.web.system.entity.AuthFreelancer;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-18 17:22
 **/
public interface AuthFreelancerMapper extends BaseMapper<AuthFreelancer> {

    /**
     * 根据用户id获取自由职业者认证信息
     * @param userId 用户id
     * @return 企业用户认证信息
     */
    AuthFreelancer findByUserId(Long userId);

    /**
     * 更新自由职业者用户实名认证状态
     * @param userId 用户id
     */
    void updateLancerAuthStatus(Long userId);
}