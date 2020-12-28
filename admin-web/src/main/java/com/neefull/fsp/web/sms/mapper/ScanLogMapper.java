package com.neefull.fsp.web.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.sms.entity.ScanLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/26  14:35
 */

@Component
public interface ScanLogMapper extends BaseMapper<ScanLog> {

    void updateStatus(@Param("id") Integer id, @Param("status") String status);


    IPage<ScanLog> getPageScanLog(IPage<ScanLog> scanLogPage, ScanLog scanLog);
}
