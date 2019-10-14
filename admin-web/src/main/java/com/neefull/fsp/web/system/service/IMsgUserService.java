package com.neefull.fsp.web.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.system.entity.MsgUser;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-10-11 17:02
 **/
public interface IMsgUserService extends IService<MsgUser> {

    /**
     * 根据消息id和用户id生成一条绑定消息数据
     * @param msgId 消息id
     * @param userId 用户id
     */
    void saveMsgUserByUserIdAndMsgId(long msgId, long userId);
}