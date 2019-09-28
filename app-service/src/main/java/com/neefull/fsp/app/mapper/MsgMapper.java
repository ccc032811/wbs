package com.neefull.fsp.app.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.app.entity.MsgInfo;
import com.neefull.fsp.app.entity.MsgWapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author pei.wang
 */
@DS("slave")
public interface MsgMapper extends BaseMapper<MsgInfo> {


    /**
     * 查询用户未读消息数量
     *
     * @param msgWapper
     * @return
     */
    @Select("SELECT COUNT(1) count FROM t_msg_info a LEFT JOIN t_msg_user b ON a.id=b.msg_id WHERE  b.user_id=#{msgWapper.userId} AND b.read_state=0")
    MsgWapper getNoreadCount(@Param("msgWapper") MsgWapper msgWapper);

    /**
     * 获取用户消息列表
     *
     * @param msgInfo
     * @return
     */
    List<MsgInfo> getUserMsgs(@Param("msgInfo") MsgInfo msgInfo);

    /**
     * 设置消息已读
     *
     * @param msgInfo
     * @return
     */
    @Delete("update t_msg_user set read_state=1 where user_id=#{msgInfo.userId} and msg_id=#{msgInfo.id}")
    boolean toReaded(@Param("msgInfo") MsgInfo msgInfo);

    /**
     * 设置消息删除
     *
     * @param msgInfo
     * @return
     */
    @Delete("update t_msg_user set delete_state=1 where user_id=#{msgInfo.userId} and msg_id=#{msgInfo.id}")
    boolean toDeleted(@Param("msgInfo") MsgInfo msgInfo);
}
