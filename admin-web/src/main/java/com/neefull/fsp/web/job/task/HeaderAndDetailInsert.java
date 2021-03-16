package com.neefull.fsp.web.job.task;


import com.neefull.fsp.web.sms.entity.Detail;
import com.neefull.fsp.web.sms.entity.Header;
import com.neefull.fsp.web.sms.entity.ScanLog;
import com.neefull.fsp.web.sms.service.IDetailService;
import com.neefull.fsp.web.sms.service.IHeaderService;
import com.neefull.fsp.web.sms.service.IScanLogService;
import com.neefull.fsp.web.sms.utils.XmlUtils;
import com.neefull.fsp.web.system.entity.Opinion;
import com.neefull.fsp.web.system.service.IOpinionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**  解析soap获取的信息
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
    @Autowired
    private IOpinionService opinionService;

    @Transactional
    public void insertSapMsg(){
        //查询没有解析出来的信息

        List<ScanLog> headerList = scanLogService.selectScanLogInsert();

        List<Opinion> plant = opinionService.getOpinions("Plant");
        List<String> plants = plant.stream().map(Opinion::getName).collect(Collectors.toList());

        for (ScanLog scanLog : headerList) {

            Header header = XmlUtils.resolverSapMessage(scanLog.getDeliveryResponse(),plants);
            //入库
            headerService.insertHeader(header);
            List<Detail> detailList = header.getDetailList();
            for (Detail detail : detailList) {
                detailService.insertDetail(detail);
            }
        }

        //更新状态为以解析
        scanLogService.updateScanLogStatus(headerList);
    }

}
