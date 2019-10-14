package com.neefull.fsp.web.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.web.system.entity.MsgInfo;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-10-11 16:39
 **/
public interface MsgInfoMapper extends BaseMapper<MsgInfo> {

    int saveReturnPrimaryKey(MsgInfo msgInfo);
}