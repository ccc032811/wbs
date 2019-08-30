package com.neefull.fsp.api.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.api.entity.User;
import com.neefull.fsp.api.entity.UserCard;
import com.neefull.fsp.api.mapper.UserCardMapper;
import com.neefull.fsp.api.service.IUserCardService;
import com.neefull.fsp.api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserCardServiceImpl extends ServiceImpl<UserCardMapper, UserCard> implements IUserCardService {

    @Autowired
    IUserService userService;
    @Override
    @Transactional
    public int saveUserCard(UserCard userCard) {
        userCard.setCreateTime(new Date());
        userCard.setAuthStatus(1);
        User user = null;
        if (this.baseMapper.insert(userCard) > 0) {
            //更新用户信息表的绑卡状态
            user = new User();
            user.setUserId(userCard.getUserId());
            user.setCardStatus("1");
        }
        return userService.updateUser(user);
    }
}
