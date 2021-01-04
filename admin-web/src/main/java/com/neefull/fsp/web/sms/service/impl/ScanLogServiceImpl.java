package com.neefull.fsp.web.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.sms.entity.Header;
import com.neefull.fsp.web.sms.entity.Scan;
import com.neefull.fsp.web.sms.entity.ScanLog;
import com.neefull.fsp.web.sms.entity.vo.DetailScanVo;
import com.neefull.fsp.web.sms.mapper.ScanLogMapper;
import com.neefull.fsp.web.sms.service.IHeaderService;
import com.neefull.fsp.web.sms.service.IScanLogService;
import com.neefull.fsp.web.sms.service.IScanService;
import com.neefull.fsp.web.sms.utils.ScanComment;
import com.neefull.fsp.web.sms.utils.SoapProperties;
import com.neefull.fsp.web.sms.utils.SoapWsUtils;
import com.neefull.fsp.web.sms.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/26  14:34
 */

@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScanLogServiceImpl extends ServiceImpl<ScanLogMapper, ScanLog> implements IScanLogService {


    @Autowired@Lazy
    private IScanService scanService;
    @Autowired@Lazy
    private IHeaderService headerService;


    @Override
    public ScanLog queryDnByDelivery(String delivery) {
        return this.baseMapper.selectOne(new QueryWrapper<ScanLog>().eq("delivery",delivery));
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void updateStatus(Integer id, String status) {
        this.baseMapper.updateStatus(id,status);
    }


    @Override
    public List<ScanLog> selectScanLogInsert() {
        return this.baseMapper.selectList(new QueryWrapper<ScanLog>().eq("status", ScanComment.STATUS_ONE));

    }


    @Override
    @Transactional
    public void updateScanLogStatus(List<ScanLog> headerList) {
        for (ScanLog scanLog : headerList) {
            this.baseMapper.updateStatus(scanLog.getId(),ScanComment.STATUS_TWO);
        }
    }


    @Override
    public IPage<ScanLog> queryScanLogList(ScanLog scanLog) {
        ScanLog singleScanLog = (ScanLog) ScanComment.containPlant(scanLog);
        IPage<ScanLog> scanLogPage = new Page<ScanLog>(singleScanLog.getPageNum(), singleScanLog.getPageSize());
        return this.baseMapper.getPageScanLog(scanLogPage,singleScanLog);

    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public DetailScanVo getDnMessageByDelivery(String delivery) {
        DetailScanVo detailScanVo = new DetailScanVo();

        String message = "";
        String plant = "";
        ScanLog scanLog = queryDnByDelivery(delivery);
        List<Scan> scanList = scanService.queryScanByDelivery(delivery);

        if(scanLog!=null){
            message = scanLog.getDeliveryResponse();
            plant = XmlUtils.getTagContent(message,"<PLANT>", "</PLANT>");
        }else {
            String soapMessage = SoapWsUtils.getSoapMessage(delivery);
            try {
                message = SoapWsUtils.callWebService(SoapProperties.SOAPURL,soapMessage);

                String soldToParty = XmlUtils.getTagContent(message, "<SOLD_TO_PARTY>", "</SOLD_TO_PARTY>");
                String shipToParty = XmlUtils.getTagContent(message, "<SHIP_TO_PARTY>", "</SHIP_TO_PARTY>");
                plant = XmlUtils.getTagContent(message,"<PLANT>", "</PLANT>");

                if(StringUtils.isNotEmpty(soldToParty)&&StringUtils.isNotEmpty(shipToParty)){
                    ScanLog newScanLog = new ScanLog();
                    newScanLog.setDelivery(delivery);
                    newScanLog.setDeliveryResponse(message);
                    newScanLog.setPlant(plant);
                    this.baseMapper.insert(newScanLog);

                    headerService.insertHeaderAndDetail(message,newScanLog.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        detailScanVo.setPlant(plant);
        detailScanVo.setDetailVoList(XmlUtils.resolverDetail(message));
        if(CollectionUtils.isNotEmpty(scanList)){
            detailScanVo.setStatus("1");  //有扫描记录
        }else {
            detailScanVo.setStatus("2");  //没有扫描记录
        }
        return detailScanVo;
    }



}
