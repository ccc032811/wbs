package com.neefull.fsp.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.api.entity.UserAuthinfo;

/**
 * @author pei.wang
 */
public interface IUserAuthService extends IService<UserAuthinfo> {

    /**
     * 保存用户认证信息
     * @param userAuthinfo
     */
    int saveUserAuthInfo(UserAuthinfo userAuthinfo);
    /**
     * 修改用户认证信息
     * @param userAuthinfo
     */
    int updateAuthUserInfo(UserAuthinfo userAuthinfo);

    /**
     * 查询用户认证信息
     * @param userAuthinfo
     */
    UserAuthinfo queryUserInfo(UserAuthinfo userAuthinfo);

   /* *//**
     * 获取用户图片
     * @param userAuthinfo
     * @return
     *//*
    String queryImageInfo(long userId,int imgTYpe);
*/
}
