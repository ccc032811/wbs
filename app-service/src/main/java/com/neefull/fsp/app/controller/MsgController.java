package com.neefull.fsp.app.controller;

import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.entity.MsgInfo;
import com.neefull.fsp.app.entity.MsgWapper;
import com.neefull.fsp.app.mapper.MsgMapper;
import com.neefull.fsp.common.entity.FebsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author pei.wang
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    MsgMapper msgMapper;

    /**
     * 查询用户未读消息数量
     *
     * @param msgWapper
     * @return
     */
    @RequestMapping(value = "/getNoreadCount", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String getNoreadCount(@RequestBody MsgWapper msgWapper, HttpServletRequest httpServletRequest) {
        long userId = (long) httpServletRequest.getAttribute("userId");
        msgWapper.setUserId(userId);
        msgWapper = msgMapper.getNoreadCount(msgWapper);
        msgWapper.setUserId(userId);
        return new FebsResponse().success().data(msgWapper).message("查询完成").toJsonNoNull();
    }

    /**
     * 查询用户消息
     *
     * @param msgInfo
     * @return
     */
    @RequestMapping(value = "/getUserMsgs", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String getUserMsgs(@RequestBody MsgInfo msgInfo, HttpServletRequest httpServletRequest) {
        long userId = (long) httpServletRequest.getAttribute("userId");
        msgInfo.setUserId(userId);
        List<MsgInfo> msgInfos = msgMapper.getUserMsgs(msgInfo);
        return new FebsResponse().success().data(msgInfos).message("查询完成").toJsonNoNull();
    }

    /**
     * 设置消息已读
     *
     * @param msgInfo
     * @return
     */
    @RequestMapping(value = "/toReaded", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String toReaded(@RequestBody MsgInfo msgInfo, HttpServletRequest httpServletRequest) {
        long userId = (long) httpServletRequest.getAttribute("userId");
        msgInfo.setUserId(userId);
        msgInfo.setModifyTime(new Timestamp(new Date().getTime()));
        if (msgMapper.toReaded(msgInfo)) {
            return new FebsResponse().success().data("").message("操作成功").toJson();
        } else {
            return new FebsResponse().fail().data("").message("操作失败").toJson();
        }
    }

    /**
     * 设置消息删除
     *
     * @param msgInfo
     * @return
     */
    @RequestMapping(value = "/toDeleted", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String toDeleted(@RequestBody MsgInfo msgInfo, HttpServletRequest httpServletRequest) {
        long userId = (long) httpServletRequest.getAttribute("userId");
        msgInfo.setUserId(userId);
        msgInfo.setModifyTime(new Timestamp(new Date().getTime()));
        if (msgMapper.toDeleted(msgInfo)) {
            return new FebsResponse().success().data("").message("操作成功").toJson();
        } else {
            return new FebsResponse().fail().data("").message("操作失败").toJson();
        }
    }


}
