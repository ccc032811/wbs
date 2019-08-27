package com.neefull.fsp.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.api.entity.AuthFreelancer;

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
    AuthFreelancer queryUserInfo(AuthFreelancer AuthFreelancer);

   /* *//**
     * 获取自由职业者图片
     * @param AuthFreelancer
     * @return
     *//*
    String queryImageInfo(long userId,int imgTYpe);
*/
}
