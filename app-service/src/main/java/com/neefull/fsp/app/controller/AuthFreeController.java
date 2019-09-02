package com.neefull.fsp.app.controller;


import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.entity.AuthFreelancer;
import com.neefull.fsp.app.exception.BizException;
import com.neefull.fsp.app.mapper.AuthFreeMapper;
import com.neefull.fsp.app.service.IAuthFreeService;
import com.neefull.fsp.common.config.CardValidConfig;
import com.neefull.fsp.common.entity.BankCard;
import com.neefull.fsp.common.entity.FebsResponse;
import com.neefull.fsp.common.util.CertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 平台账户
 *
 * @author pei.wang
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
public class AuthFreeController {
    @Autowired
    IAuthFreeService authFreeService;
    @Autowired
    AuthFreeMapper authFreeMapper;
    @Autowired
    CardValidConfig cardValidConfig;
    @Autowired
    CertUtil certUtil;

    @RequestMapping(value = "/freelancerCertification", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String freelancerCertification(@RequestBody AuthFreelancer authFreelancer, HttpServletRequest httpServletRequest) throws BizException {
        long userId = (long) httpServletRequest.getAttribute("userId");
        authFreelancer.setUserId(userId);
        int result = authFreeService.saveAuthFreelancer(authFreelancer);
        if (result > 0) {
            //判断是否开启自动验证 1代表开启
            if ("1".equals(cardValidConfig.getTurnOn())) {
                BankCard bankCard = new BankCard();
                bankCard.setRealName(authFreelancer.getRealName());
                bankCard.setCardNo(authFreelancer.getCardNo());
                bankCard.setCertNo(authFreelancer.getIdNo());
                bankCard.setLinkNo(authFreelancer.getMobile());
                try {
                    Map<String, String> resultMap = certUtil.userCardCert(cardValidConfig, bankCard);
                    //更新数据库信息
                    if ("0".equals(resultMap.get("code"))) {
                        authFreelancer.setAuthStatus(1);
                        //更新用户真实姓名
                        authFreelancer.setRealName(bankCard.getRealName());
                    } else {
                        authFreelancer.setRemark(resultMap.get("msg"));
                    }
                    authFreeService.updateAuthUserInfo(authFreelancer);

                } catch (Exception e) {
                    throw new BizException(e.getMessage());
                }
            }
            return new FebsResponse().success().data(result).message("申请实名认证成功,请等待审核").toJson();
        } else {
            return new FebsResponse().fail().data(result).message("申请认证失败,修改相关信息").toJson();

        }

    }

    /**
     * 获取自由职业者提交的认证信息
     *
     * @param httpServletRequest
     * @return
     * @throws BizException
     */

    @RequestMapping(value = "/updateAuthFreelancer", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String updateAuthUserInfo(@RequestBody AuthFreelancer authFreelancer, HttpServletRequest httpServletRequest) throws BizException {
        long userId = (long) httpServletRequest.getAttribute("userId");
        //long userId = 9;
        authFreelancer.setUserId(userId);
        authFreelancer.setAuthStatus(0);
        if (authFreeService.updateAuthUserInfo(authFreelancer) > 0) {
            //判断是否开启自动验证 1代表开启
            if ("1".equals(cardValidConfig.getTurnOn())) {
                BankCard bankCard = new BankCard();
                bankCard.setRealName(authFreelancer.getRealName());
                bankCard.setCardNo(authFreelancer.getCardNo());
                bankCard.setCertNo(authFreelancer.getIdNo());
                bankCard.setLinkNo(authFreelancer.getMobile());
                try {
                    Map<String, String> resultMap = certUtil.userCardCert(cardValidConfig, bankCard);
                    //更新数据库信息
                    if ("0".equals(resultMap.get("code"))) {
                        authFreelancer.setAuthStatus(1);
                    } else {
                        authFreelancer.setRemark(resultMap.get("msg"));
                    }
                    authFreeService.updateAuthUserInfo(authFreelancer);

                } catch (Exception e) {
                    throw new BizException(e.getMessage());
                }
            }
            return new FebsResponse().success().data("").message("修改实名认证成功,请等待审核").toJson();
        } else {
            return new FebsResponse().fail().data("").message("网络故障,请重新提交").toJson();
        }
    }
}
