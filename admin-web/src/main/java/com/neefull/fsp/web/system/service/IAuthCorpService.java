package com.neefull.fsp.web.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.system.entity.AuthCorp;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-18 13:54
 **/
public interface IAuthCorpService extends IService<AuthCorp> {


    /**
     * 根据用户id获取企业用户认证信息
     * @param userId 用户id
     * @return 企业用户认证信息
     */
    AuthCorp findByUserId(Long userId);

    /**
     * 更新企业用户表信息
     * @param authCorp 企业用户信息
     */
    void updateAuthCorpByUserId(AuthCorp authCorp);
}