package com.neefull.fsp.web.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.system.entity.AuthCorp;
import com.neefull.fsp.web.system.mapper.AuthCorpMapper;
import com.neefull.fsp.web.system.service.IAuthCorpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-18 13:55
 **/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AuthCorpServiceImpl extends ServiceImpl<AuthCorpMapper, AuthCorp> implements IAuthCorpService {

    /**
     * 根据用户id获取企业用户认证信息
     * @param userId 用户id
     * @return 企业用户认证信息
     */
    @Override
    public AuthCorp findByUserId(Long userId) {
        return this.baseMapper.findByUserId(userId);
    }

    /**
     * 更新企业用户表信息
     * @param authCorp 企业用户信息
     */
    @Override
    public void updateAuthCorpByUserId(AuthCorp authCorp) {
        this.baseMapper.updateAuthCorpByUserId(authCorp);
    }
}