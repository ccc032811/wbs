package com.neefull.fsp.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.api.entity.UserInfo;

/**
 * @author pei.wang
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 通过用户ID查找用户详细信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfo findUserInfo(Long userId);

    /**
     * 更新用户头像
     *
     */
    void updateUserInfo(UserInfo userInfo);


    /**
     * 完善用户信息
     * @return
     */
    boolean perfectingInformation(UserInfo userInfo);

}
