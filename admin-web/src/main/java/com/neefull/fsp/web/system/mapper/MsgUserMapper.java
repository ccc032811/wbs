package com.neefull.fsp.web.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.web.system.entity.MsgUser;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-10-11 17:03
 **/
public interface MsgUserMapper extends BaseMapper<MsgUser> {

    /**
     * 插入一条消息用户绑定数据
     * @param msgUser
     * @return
     */
    int saveByMsgUser(MsgUser msgUser);
}