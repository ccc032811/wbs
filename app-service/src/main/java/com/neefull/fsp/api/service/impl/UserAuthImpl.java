package com.neefull.fsp.api.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.api.entity.UserAuthinfo;
import com.neefull.fsp.api.mapper.UserAuthMapper;
import com.neefull.fsp.api.service.IUserAuthService;
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
public class UserAuthImpl extends ServiceImpl<UserAuthMapper, UserAuthinfo> implements IUserAuthService {

    @Override
    public int saveUserAuthInfo(UserAuthinfo userAuthinfo) {
        userAuthinfo.setCreateTime(new Date());
        return this.baseMapper.insert(userAuthinfo);
    }

    @Override
    public int updateAuthUserInfo(UserAuthinfo userAuthinfo) {
        LambdaUpdateWrapper<UserAuthinfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(UserAuthinfo::getUserId, userAuthinfo.getUserId());
        userAuthinfo.setMidifyTime(new Date());
        return this.baseMapper.update(userAuthinfo, lambdaUpdateWrapper);
    }

    @Override
    public UserAuthinfo queryUserInfo(UserAuthinfo userAuthinfo) {
        LambdaQueryWrapper<UserAuthinfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAuthinfo::getUserId, userAuthinfo.getUserId());
        return this.baseMapper.selectOne(lambdaQueryWrapper);
    }


   /* @Override
    public String queryImageInfo(long userId, int imgTYpe) {
        QueryWrapper<UserAuthinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select()
        return null;
    }*/
}
