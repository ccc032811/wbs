package com.neefull.fsp.web.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.system.entity.MsgInfo;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-10-11 16:37
 **/
public interface IMsgInfoService extends IService<MsgInfo> {

    /**
     * 插入后返回主键ID
     * @param msgInfo
     * @return
     */
    MsgInfo saveReturnPrimaryKey(MsgInfo msgInfo);
}