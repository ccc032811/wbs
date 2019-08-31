package com.neefull.fsp.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.AuthFreelancer;
import com.neefull.fsp.app.entity.User;
import com.neefull.fsp.app.mapper.AuthFreeMapper;
import com.neefull.fsp.app.service.IAuthFreeService;
import com.neefull.fsp.app.service.IUserService;
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
    @Override
    @Transactional
    public int saveAuthFreelancer(AuthFreelancer AuthFreelancer) {
        return this.baseMapper.insert(AuthFreelancer);
    }

    @Autowired
    IUserService userService;
    @Override
    @Transactional
    public int updateAuthUserInfo(AuthFreelancer authFreelancer) {
        LambdaUpdateWrapper<AuthFreelancer> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(AuthFreelancer::getUserId, authFreelancer.getUserId());
        User user = new User();
        user.setUserId(authFreelancer.getUserId());
        user.setAuthStatus(authFreelancer.getAuthStatus());
        user.setCardStatus(authFreelancer.getAuthStatus());
        userService.updateUser(user);
        return this.baseMapper.update(authFreelancer, lambdaUpdateWrapper);
    }
    @Override
    public AuthFreelancer queryUserInfo(AuthFreelancer authFreelancer) {
        LambdaQueryWrapper<AuthFreelancer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AuthFreelancer::getUserId, authFreelancer.getUserId());
        return this.baseMapper.selectOne(lambdaQueryWrapper);
    }


   /* @Override
    public String queryImageInfo(long userId, int imgTYpe) {
        QueryWrapper<AuthFreelancer> queryWrapper = new QueryWrapper<>();
        queryWrapper.select()
        return null;
    }*/
}
