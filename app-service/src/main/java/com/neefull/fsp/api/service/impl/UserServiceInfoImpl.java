package com.neefull.fsp.api.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.api.entity.UserInfo;
import com.neefull.fsp.api.mapper.UserInfoMapper;
import com.neefull.fsp.api.service.IUserInfoService;
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
public class UserServiceInfoImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {


    /**
     * 根据用户ID查询用户详细信息
     * @param userId 用户ID
     * @return
     */
    @Override
    public UserInfo findUserInfo(Long userId) {
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getUserId, userId);
        return this.baseMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {

    }

    /**
     * 完善用户信息
     * 用户信息不存在时插入，存在是更新
     *
     * @param userInfo
     * @return
     */
    @Override
    public boolean perfectingInformation(UserInfo userInfo) {

        if (null == findUserInfo(userInfo.getUserId())) {
            userInfo.setCreateTime(new Date());
            return save(userInfo);
        } else {
            LambdaUpdateWrapper<UserInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<UserInfo>();
            lambdaUpdateWrapper.eq(UserInfo::getUserId, userInfo.getUserId());
            userInfo.setMotifyTime(new Date());
            return update(userInfo, lambdaUpdateWrapper);
        }

    }
}
