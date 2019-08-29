package com.neefull.fsp.api.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.api.entity.AuthCorp;
import com.neefull.fsp.api.mapper.AuthCorpMapper;
import com.neefull.fsp.api.service.IAuthCorpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pei.wang
 */
@DS("master")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AuthCorpServiceImpl extends ServiceImpl<AuthCorpMapper, AuthCorp> implements IAuthCorpService {


    @Override
    public int saveAuthCorp(AuthCorp authCorp) {
        return this.baseMapper.insert(authCorp);
    }

    @Override
    public int updateAuthCorp(AuthCorp authCorp) {
        LambdaUpdateWrapper<AuthCorp> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(AuthCorp::getUserId, authCorp.getUserId());
        return this.baseMapper.update(authCorp, lambdaUpdateWrapper);
    }

    @Override
    public AuthCorp queryCorpByUserId(AuthCorp authCorp) {
        LambdaQueryWrapper<AuthCorp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AuthCorp::getUserId, authCorp.getUserId());
        return this.baseMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public List<AuthCorp> queryAllCorpAuth(AuthCorp authCorp) {
        LambdaQueryWrapper<AuthCorp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (null != authCorp.getCorpName()) {
            lambdaQueryWrapper.like(AuthCorp::getCorpName, authCorp.getCorpName());
        }
        if (null != authCorp.getAuthStatus()) {
            lambdaQueryWrapper.like(AuthCorp::getAuthStatus, authCorp.getAuthStatus());
        }
        lambdaQueryWrapper.orderByDesc(AuthCorp::getCreateTime, AuthCorp::getAuthStatus);
        return this.baseMapper.selectList(lambdaQueryWrapper);
    }
}
