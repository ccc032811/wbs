package com.neefull.fsp.web.job.task;


import com.neefull.fsp.web.sms.entity.Detail;
import com.neefull.fsp.web.sms.entity.Header;
import com.neefull.fsp.web.sms.entity.ScanLog;
import com.neefull.fsp.web.sms.service.IDetailService;
import com.neefull.fsp.web.sms.service.IHeaderService;
import com.neefull.fsp.web.sms.service.IScanLogService;
import com.neefull.fsp.web.sms.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/26  18:02
 */

@Slf4j
@Component
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class HeaderAndDetailInsert {

    @Autowired
    private IScanLogService scanLogService;
    @Autowired
    private IHeaderService headerService;
    @Autowired
    private IDetailService detailService;

//    @Transactional
//    public void insertHeaderAndDetail(){
//        List<ScanLog> headerList = scanLogService.selectScanLogInsert();
//        for (ScanLog scanLog : headerList) {
//            Header header = XmlUtils.resolverSapMessage(scanLog.getDeliveryResponse());
//            headerService.insertHeader(header);
//            List<Detail> detailList = header.getDetailList();
//            for (Detail detail : detailList) {
//                detailService.insertDetail(detail);
//            }
//        }
//        scanLogService.updateScanLogStatus(headerList);
//    }

}
