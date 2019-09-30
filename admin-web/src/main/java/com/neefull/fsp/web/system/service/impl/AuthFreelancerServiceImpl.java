package com.neefull.fsp.web.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.common.config.QiniuConfig;
import com.neefull.fsp.web.system.entity.AuthFreelancer;
import com.neefull.fsp.web.system.mapper.AuthFreelancerMapper;
import com.neefull.fsp.web.system.service.IAuthFreelancerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-18 17:21
 **/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AuthFreelancerServiceImpl extends ServiceImpl<AuthFreelancerMapper, AuthFreelancer> implements IAuthFreelancerService {

    @Autowired
    QiniuConfig qiniuConfig;

    /**
     * 根据用户id获取自由职业者认证信息
     * @param userId 用户id
     * @return 企业用户认证信息
     */
    @Override
    public AuthFreelancer findByUserId(Long userId) {
        AuthFreelancer lancer = this.baseMapper.findByUserId(userId);
        try {
            if(lancer != null){
                //七牛图片转换成路径
                if(!StringUtils.isEmpty(lancer.getIdImage1())){
                    lancer.setIdImage1(qiniuConfig.getOssManager().getDownUrl(qiniuConfig, lancer.getIdImage1()));
                }
                if(!StringUtils.isEmpty(lancer.getIdImage2())){
                    lancer.setIdImage2(qiniuConfig.getOssManager().getDownUrl(qiniuConfig, lancer.getIdImage2()));
                }
                if(!StringUtils.isEmpty(lancer.getCardImage1())){
                    lancer.setCardImage1(qiniuConfig.getOssManager().getDownUrl(qiniuConfig, lancer.getCardImage1()));
                }
                if(!StringUtils.isEmpty(lancer.getCardImage2())){
                    lancer.setCardImage2(qiniuConfig.getOssManager().getDownUrl(qiniuConfig, lancer.getCardImage2()));
                }
            }
        }catch (UnsupportedEncodingException e){
            new Exception("链接Oss网络故障");
        }
        return lancer;
    }

    /**
     * 更新自由职业者用户实名认证状态
     * @param userId 用户id
     */
    @Override
    public void updateLancerAuthStatus(Long userId) {
        this.baseMapper.updateLancerAuthStatus(userId);
    }
}