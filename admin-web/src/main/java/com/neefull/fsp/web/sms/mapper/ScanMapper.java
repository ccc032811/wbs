package com.neefull.fsp.web.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.sms.entity.Scan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/1  11:03
 */

@Component
public interface ScanMapper extends BaseMapper<Scan> {

    IPage<Scan> queryScanDetailList(IPage<Scan> scanPage,Scan scan);

    IPage<Scan> getScanInfoList(IPage<Scan> scanPage, Scan scan);

    void updateScanStatus(@Param("id") Integer id, @Param("status") String status);

}
