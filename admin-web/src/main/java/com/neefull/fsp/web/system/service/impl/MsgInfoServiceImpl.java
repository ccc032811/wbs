package com.neefull.fsp.web.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.system.entity.MsgInfo;
import com.neefull.fsp.web.system.mapper.MsgInfoMapper;
import com.neefull.fsp.web.system.mapper.MsgUserMapper;
import com.neefull.fsp.web.system.service.IMsgInfoService;
import com.neefull.fsp.web.system.service.IMsgUserService;
import com.neefull.fsp.web.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-10-11 16:38
 **/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MsgInfoServiceImpl extends ServiceImpl<MsgInfoMapper, MsgInfo> implements IMsgInfoService {

    @Autowired
    private IMsgUserService msgUserService;
    @Autowired
    private IUserService userService;
    @Autowired
    private MsgUserMapper msgUserMapper;

    /**
     * 插入后返回主键ID
     * @param msgInfo
     * @return
     */
    @Override
    public MsgInfo saveReturnPrimaryKey(MsgInfo msgInfo) {
        this.baseMapper.saveReturnPrimaryKey(msgInfo);
        return msgInfo;
    }

    /**
     * 发送消息
     * @param msgInfo
     * @return
     */
    @Override
    public boolean sendMsg(MsgInfo msgInfo) {
        boolean sendFlag = false;
        try {
            msgInfo = saveReturnPrimaryKey(msgInfo);   //插入消息表

            if(msgInfo.getUserIds().contains("allUsers")){  //所有用户
                msgUserMapper.saveByMsgIdAndUserList(msgInfo.getId(), userService.getAllUseUserLst());
            }else{
                String[] arrUserIds = msgInfo.getUserIds().split(",");
                for(String id : arrUserIds){
                    msgUserService.saveMsgUserByUserIdAndMsgId(msgInfo.getId(), Long.valueOf(id));
                }
            }
            sendFlag = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return sendFlag;
    }
}