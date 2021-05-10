package com.neefull.fsp.web.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.sms.entity.Scan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/1  11:03
 */

@Mapper
public interface ScanMapper extends BaseMapper<Scan> {

    IPage<Scan> queryScanDetailList(IPage<Scan> scanPage,Scan scan);

    IPage<Scan> getScanInfoList(IPage<Scan> scanPage, Scan scan);

    void updateScanStatus(@Param("id") Integer id, @Param("status") String status);

    List<Scan> queryScanAndCountByDelivery(@Param("delivery") String delivery);

    void deleteScanById(@Param("id") Integer id, @Param("del") String del);

    void updateStatusByDelivery(@Param("delivery") String delivery, @Param("status") String status);

    Scan selectScanByDeliveryAndMatCode(@Param("delivery") String delivery, @Param("matCode") String matCode, @Param("batch") String batch, @Param("status") String status);

    List<Scan> downScanExcel(Scan scan);

    List<String> queryBoxTypeByDeliveryAndMatCode(@Param("delivery") String delivery, @Param("material") String material);

    Scan selectScanByDeliveryAndMatCodeAndBatchAndBoxCode(@Param("delivery") String delivery, @Param("matCode") String matCode, @Param("batch") String batch, @Param("boxCode") String boxCode, @Param("statusOne") String statusOne);
}
