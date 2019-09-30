package com.neefull.fsp.web.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.common.config.QiniuConfig;
import com.neefull.fsp.web.system.entity.AuthCorp;
import com.neefull.fsp.web.system.mapper.AuthCorpMapper;
import com.neefull.fsp.web.system.service.IAuthCorpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-18 13:55
 **/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AuthCorpServiceImpl extends ServiceImpl<AuthCorpMapper, AuthCorp> implements IAuthCorpService {

    @Autowired
    QiniuConfig qiniuConfig;

    /**
     * 根据用户id获取企业用户认证信息
     * @param userId 用户id
     * @return 企业用户认证信息
     */
    @Override
    public AuthCorp findByUserId(Long userId) {
        AuthCorp corp = this.baseMapper.findByUserId(userId);
        try {
            if(corp != null){
                if(!StringUtils.isEmpty(corp.getBusinessLience())){
                    corp.setBusinessLience(qiniuConfig.getOssManager().getDownUrl(qiniuConfig, corp.getBusinessLience()));
                }
            }
        }catch (UnsupportedEncodingException e){
            new Exception("链接Oss网络故障");
        }
        return corp;
    }

    /**
     * 更新企业用户表信息
     * @param authCorp 企业用户信息
     */
    @Override
    public void updateAuthCorpByUserId(AuthCorp authCorp) {
        this.baseMapper.updateAuthCorpByUserId(authCorp);
    }
}