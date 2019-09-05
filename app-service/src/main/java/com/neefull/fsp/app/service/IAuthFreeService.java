package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.AuthFreelancer;

/**
 * @author pei.wang
 */
public interface IAuthFreeService extends IService<AuthFreelancer> {

    /**
     * 保存自由职业者认证信息
     * @param AuthFreelancer
     */
    int saveAuthFreelancer(AuthFreelancer AuthFreelancer);
    /**
     * 修改自由职业者认证信息
     * @param AuthFreelancer
     */
    int updateAuthUserInfo(AuthFreelancer AuthFreelancer);

    /**
     * 查询自由职业者认证信息
     * @param AuthFreelancer
     */
    AuthFreelancer getAuthUserInfo(AuthFreelancer AuthFreelancer);

}
