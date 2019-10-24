package com.neefull.fsp.web.system.controller;

import com.neefull.fsp.web.common.annotation.Log;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.common.utils.FebsUtil;
import com.neefull.fsp.web.system.entity.MsgInfo;
import com.neefull.fsp.web.system.service.IMsgInfoService;
import com.neefull.fsp.web.system.service.IMsgUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-10-14 14:57
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("message")
public class MessageController extends BaseController {

    @Autowired
    private IMsgInfoService msgInfoService;


    @Log("发送消息")
    @PostMapping("submitMessageSend")
    @RequiresPermissions("message:send")
    public FebsResponse submitMessageSend(MsgInfo msgInfo) throws FebsException {
        try {
            boolean sendFlag = false;
            if(!StringUtils.isEmpty(msgInfo.getUserIds())){
                sendFlag = msgInfoService.sendMsg(msgInfo);
                if(sendFlag){
                    return new FebsResponse().success();
                }else{
                    return new FebsResponse().fail();
                }
            }else{
                return new FebsResponse().fail().data("请选择发送的用户!");
            }
        } catch (Exception e) {
            String message = "操作失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}