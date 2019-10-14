package com.neefull.fsp.web.system.utils;

import com.neefull.fsp.common.util.DateUtils;
import com.neefull.fsp.web.system.entity.MsgInfo;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-10-12 13:12
 **/
@Component
public class MsgContentUtils {

    //消息标题 => 实名认证通过
    public static final String TITLE_AUTH_SUCCESS = "实名认证通过";
    //消息标题 => 结算成功
    public static final String TITLE_SETTLE_SUCCESS = "结算成功";

    /**
     * 获取实名认证成功后的消息内容
     * @return 消息内容
     */
    public static String getAuthSuccessMsg(){
        return "恭喜您于" + DateUtils.getDateWithFormat("yyyy年MM月dd日") + "认证通过";
    }

    /**
     * 获取结算成功后的消息内容
     * @param projectName 项目名称
     * @param amount 金额
     * @return 消息内容
     */
    public static String getSettleSuccessMsg(String projectName, String amount){
        return "您于" + DateUtils.getDateWithFormat("yyyy年MM月dd日")
                + "从【"+projectName+"】中结算成功，金额：¥"+amount;
    }

    /**
     * 生成实名认证成功的消息实体类
     * @return 消息实体类
     */
    public static MsgInfo getAuthSuccessMsgInfo(){
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setTitle(TITLE_AUTH_SUCCESS);
        msgInfo.setContent(getAuthSuccessMsg());
        msgInfo.setMsgType(MsgInfo.MSG_TYPE_AUTH);
        msgInfo.setCreateTime(new Date());
        msgInfo.setModifyTime(new Date());
        return msgInfo;
    }

    /**
     * 生成结算成功的消息实体类
     * @param projectName 项目名称
     * @param amount 金额
     * @return 消息实体类
     */
    public static MsgInfo getSettleSuccessMsgInfo(String projectName, String amount){
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setTitle(TITLE_SETTLE_SUCCESS);
        msgInfo.setContent(getSettleSuccessMsg(projectName, amount));
        msgInfo.setMsgType(MsgInfo.MSG_TYPE_SETTLE);
        msgInfo.setCreateTime(new Date());
        msgInfo.setModifyTime(new Date());
        return msgInfo;
    }
}