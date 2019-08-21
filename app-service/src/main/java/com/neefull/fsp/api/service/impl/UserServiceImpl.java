package com.neefull.fsp.api.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.common.core.util.EncryptUtil;
import com.neefull.common.core.util.SerialNumberUtil;
import com.neefull.fsp.api.config.ServConstants;
import com.neefull.fsp.api.entity.User;
import com.neefull.fsp.api.entity.UserInfo;
import com.neefull.fsp.api.exception.ErrorException;
import com.neefull.fsp.api.mapper.UserMapper;
import com.neefull.fsp.api.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Override
    public User findByName(String username) {
        return this.baseMapper.findByName(username);
    }

    @Override
    public User findByMobile(String mobile) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getMobile, mobile);
        return this.baseMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public User findUserDetail(Long userId) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId, userId);
        User userDetail = this.baseMapper.selectOne(lambdaQueryWrapper);
        return userDetail;
    }


    @Override
    @Transactional
    public boolean createUser(User user) {
        try {
            user.setCreateTime(new Date());
            user.setStatus(User.STATUS_VALID);
            user.setAvatar(User.DEFAULT_AVATAR);
            user.setTheme(User.THEME_BLACK);
            user.setIsTab(User.TAB_OPEN);
            //设置个默认用户
            String default_username = SerialNumberUtil.getNextSerialNumber("游客");
            user.setUsername(default_username);
            user.setPassword(EncryptUtil.encrypt(null == user.getPassword() ? User.DEFAULT_PASSWORD : user.getPassword(), ServConstants.AES_KEY));
            return save(user);
        } catch (RuntimeException e) {
            throw new ErrorException(e.getMessage());
        }

    }

    @Override
    @Transactional
    public boolean resetPassword(User user) {
        user.setPassword(EncryptUtil.encrypt(user.getPassword(), ServConstants.AES_KEY));
        if (this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUserId, user.getUserId())) > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    @Transactional
    public void updateAvatar(long userId, String avatar) {
        User user = new User();
        user.setAvatar(avatar);
        user.setModifyTime(new Date());
        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
    }

    @Override
    public User login(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getMobile, user.getUsername()).or().eq(User::getUsername, user.getUsername());
        lambdaQueryWrapper.eq(User::getPassword, user.getPassword());
        return this.baseMapper.selectOne(lambdaQueryWrapper);
    }

}
