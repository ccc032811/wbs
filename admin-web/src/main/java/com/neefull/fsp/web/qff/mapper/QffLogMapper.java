package com.neefull.fsp.web.qff.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neefull.fsp.web.qff.entity.QffLog;
import org.springframework.stereotype.Component;

/**
 * @Author: chengchengchu
 * @Date: 2020/1/15  11:12
 */

@Component
public interface QffLogMapper extends BaseMapper<QffLog> {


    /**查询日志
     * @param page
     * @param qffLog
     * @return
     */
    IPage<QffLog> queryLogs(Page<QffLog> page, QffLog qffLog);
}
