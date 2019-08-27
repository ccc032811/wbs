package com.neefull.fsp.api.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.api.entity.AuthFreelancer;
import com.neefull.fsp.api.mapper.AuthFreeMapper;
import com.neefull.fsp.api.service.IAuthFreeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author pei.wang
 */
@DS("master")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AuthFreeServiceImpl extends ServiceImpl<AuthFreeMapper, AuthFreelancer> implements IAuthFreeService {

    @Override
    public int saveAuthFreelancer(AuthFreelancer AuthFreelancer) {
        AuthFreelancer.setCreateTime(new Date());
        return this.baseMapper.insert(AuthFreelancer);
    }

    @Override
    public int updateAuthUserInfo(AuthFreelancer authFreelancer) {
        LambdaUpdateWrapper<AuthFreelancer> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(AuthFreelancer::getUserId, authFreelancer.getUserId());
        authFreelancer.setMidifyTime(new Date());
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
