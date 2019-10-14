package com.neefull.fsp.web.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.system.entity.MsgUser;
import com.neefull.fsp.web.system.mapper.MsgUserMapper;
import com.neefull.fsp.web.system.service.IMsgUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-10-11 17:02
 **/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MsgUserServiceImpl extends ServiceImpl<MsgUserMapper, MsgUser> implements IMsgUserService {

    /**
     * 根据消息id和用户id生成一条绑定消息数据
     * @param msgId 消息id
     * @param userId 用户id
     */
    @Override
    public void saveMsgUserByUserIdAndMsgId(long msgId, long userId) {
        MsgUser msgUser = new MsgUser();
        msgUser.setMsgId(msgId);
        msgUser.setUserId(userId);
        msgUser.setReadState(MsgUser.READ_STATE_UNREAD);
        msgUser.setDeleteState(MsgUser.DELETE_STATE_UNDELETE);
        this.baseMapper.saveByMsgUser(msgUser);
    }
}