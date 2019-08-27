package com.neefull.fsp.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.api.entity.DictBanks;
import com.neefull.fsp.api.entity.UserCard;

/**
 * @author pei.wang
 */
public interface IUserCardService extends IService<UserCard> {

    int saveUserCard(UserCard userCard);
}
