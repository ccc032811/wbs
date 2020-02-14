package com.neefull.fsp.web.qff.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.qff.entity.QffLog;
import com.neefull.fsp.web.qff.mapper.QffLogMapper;
import com.neefull.fsp.web.qff.service.IQffLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * @Author: chengchengchu
 * @Date: 2020/1/15  11:08
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QffLogServiceImpl extends ServiceImpl<QffLogMapper, QffLog> implements IQffLogService {

    @Autowired
    private QffLogMapper qffLogMapper;

    @Override
    public void addQffLog(ProceedingJoinPoint point,QffLog qffLog) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        com.neefull.fsp.web.qff.aspect.Qff logAnnotation = method.getAnnotation(com.neefull.fsp.web.qff.aspect.Qff.class);
        qffLog.setMethod(point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        if (logAnnotation != null) {
            qffLog.setOperation(logAnnotation.value());
        }
        qffLog.setRequest(JSON.toJSONString(point.getArgs()));

        save(qffLog);

    }

    @Override
    public IPage<QffLog> queryLogs(QffLog qffLog) {
        Page<QffLog> page = new Page<>(qffLog.getPageNum(),qffLog.getPageSize());
        IPage<QffLog> qffLogIPage = qffLogMapper.queryLogs(page,qffLog);
        return qffLogIPage;
    }
}
