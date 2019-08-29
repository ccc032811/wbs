package com.neefull.fsp.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.api.entity.AuthCorp;

import java.util.List;

/**
 * @author pei.wang
 */
public interface IAuthCorpService extends IService<AuthCorp> {

    /**
     * 保存企业认证信息
     *
     * @param authCorp
     */
    int saveAuthCorp(AuthCorp authCorp);

    /**
     * 修改企业认证信息
     *
     * @param authCorp
     */
    int updateAuthCorp(AuthCorp authCorp);

    /**
     * 查询企业认证信息
     *
     * @param authCorp
     */
    AuthCorp queryCorpByUserId(AuthCorp authCorp);

    /**
     * 根据条件查询所有企业认证信息
     *
     * @param authCorp
     * @return
     */
    List<AuthCorp> queryAllCorpAuth(AuthCorp authCorp);

}
