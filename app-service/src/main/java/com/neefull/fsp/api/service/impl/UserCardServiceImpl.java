package com.neefull.fsp.api.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.api.entity.UserCard;
import com.neefull.fsp.api.mapper.UserCardMapper;
import com.neefull.fsp.api.service.IUserCardService;
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

    @Override
    public int saveUserCard(UserCard userCard) {
        userCard.setCreateTime(new Date());
        userCard.setAuthStatus(1);
        return this.baseMapper.insert(userCard);
    }
}
