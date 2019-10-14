package com.neefull.fsp.web.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.system.entity.MsgInfo;
import com.neefull.fsp.web.system.mapper.MsgInfoMapper;
import com.neefull.fsp.web.system.service.IMsgInfoService;
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
}