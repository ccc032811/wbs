package com.neefull.fsp.web.qff.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.qff.entity.QffLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;


/**
 * @Author: chengchengchu
 * @Date: 2020/1/15  11:08
 */
public interface IQffLogService extends IService<QffLog> {
    /**添加日志
     * @param qffLog
     */
    @Async("febsAsyncThreadPool")
    void addQffLog(ProceedingJoinPoint point,QffLog qffLog) throws Throwable;

    /**查询日志
     * @param qffLog
     * @return
     */
    IPage<QffLog> queryLogs(QffLog qffLog);

}
