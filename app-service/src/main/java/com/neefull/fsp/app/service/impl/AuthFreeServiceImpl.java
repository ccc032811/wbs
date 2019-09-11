package com.neefull.fsp.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.AuthFreelancer;
import com.neefull.fsp.app.entity.User;
import com.neefull.fsp.app.mapper.AuthFreeMapper;
import com.neefull.fsp.app.mapper.UserMapper;
import com.neefull.fsp.app.service.IAuthFreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pei.wang
 */
@DS("master")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AuthFreeServiceImpl extends ServiceImpl<AuthFreeMapper, AuthFreelancer> implements IAuthFreeService {
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public int saveAuthFreelancer(AuthFreelancer authFreelancer) {
        int result = -1;
        authFreelancer.setAuthStatus(1);
        if (null == getAuthUserInfo(authFreelancer)) {
            result = this.baseMapper.insert(authFreelancer);
            if (-1 != result) {
                User user = new User();
                user.setAuthStatus(1);
                user.setCardStatus(1);
                user.setUserId(authFreelancer.getUserId());
                result = userMapper.updateUserAuth(user);
            }
        } else {
            result = updateAuthUserInfo(authFreelancer);
        }
        return result;
    }

    @Override
    @Transactional
    public int updateAuthUserInfo(AuthFreelancer authFreelancer) {
        LambdaUpdateWrapper<AuthFreelancer> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(AuthFreelancer::getUserId, authFreelancer.getUserId());
        int result = this.baseMapper.update(authFreelancer, lambdaUpdateWrapper);
        if (-1 != result) {
            User user = new User();
            user.setUserId(authFreelancer.getUserId());
            //成功之后，更新用户名和状态
            if (2 == authFreelancer.getAuthStatus()) {
                user.setAuthStatus(authFreelancer.getAuthStatus());
                user.setCardStatus(authFreelancer.getAuthStatus());
                user.setUsername(authFreelancer.getRealName());
                result = userMapper.updateUserAuthWithName(user);
            } else {
                //不是成功，只更新状态信息
                user.setAuthStatus(authFreelancer.getAuthStatus());
                user.setCardStatus(authFreelancer.getAuthStatus());
                result = userMapper.updateUserAuth(user);
            }

        }
        return result;
    }

    @Override
    public AuthFreelancer getAuthUserInfo(AuthFreelancer authFreelancer) {
        LambdaQueryWrapper<AuthFreelancer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AuthFreelancer::getUserId, authFreelancer.getUserId());
        return this.baseMapper.selectOne(lambdaQueryWrapper);
    }

}
