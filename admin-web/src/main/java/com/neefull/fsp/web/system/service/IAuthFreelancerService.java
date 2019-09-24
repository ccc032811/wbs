package com.neefull.fsp.web.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.system.entity.AuthFreelancer;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-18 17:21
 **/
public interface IAuthFreelancerService extends IService<AuthFreelancer> {


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