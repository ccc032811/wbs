package com.neefull.fsp.web.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.system.entity.AuthFreelancer;
import com.neefull.fsp.web.system.mapper.AuthFreelancerMapper;
import com.neefull.fsp.web.system.service.IAuthFreelancerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-18 17:21
 **/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AuthFreelancerServiceImpl extends ServiceImpl<AuthFreelancerMapper, AuthFreelancer> implements IAuthFreelancerService {

    /**
     * 根据用户id获取自由职业者认证信息
     * @param userId 用户id
     * @return 企业用户认证信息
     */
    @Override
    public AuthFreelancer findByUserId(Long userId) {
        return this.baseMapper.findByUserId(userId);
    }

    /**
     * 更新自由职业者用户实名认证状态
     * @param userId 用户id
     */
    @Override
    public void updateLancerAuthStatus(Long userId) {
        this.baseMapper.updateLancerAuthStatus(userId);
    }
}