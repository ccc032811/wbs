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
import com.neefull.fsp.web.system.entity.Opinion;
import com.neefull.fsp.web.system.service.IOpinionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired@Lazy
    private IScanLogService scanLogService;
    @Autowired
    private IOpinionService opinionService;


    @Override
    public ScanLog queryDnByDelivery(String delivery) {
        return this.baseMapper.selectOne(new QueryWrapper<ScanLog>().eq("delivery",delivery));
    }


    @Override
    @Transactional
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
//        ScanLog singleScanLog = (ScanLog) ScanComment.containPlant(scanLog);
        IPage<ScanLog> scanLogPage = new Page<ScanLog>(scanLog.getPageNum(), scanLog.getPageSize());
        return this.baseMapper.getPageScanLog(scanLogPage,scanLog);

    }

    @Override
    @Transactional
    public void insertScanLog(String message, String delivery) {

        String soldToParty = XmlUtils.getTagContent(message, "<SOLD_TO_PARTY>", "</SOLD_TO_PARTY>");
        String shipToParty = XmlUtils.getTagContent(message, "<SHIP_TO_PARTY>", "</SHIP_TO_PARTY>");
        String plant = XmlUtils.getTagContent(message,"<PLANT>", "</PLANT>");

        String reDelivery = XmlUtils.getTagContent(message, "<DELIVERY>", "</DELIVERY>");
        ScanLog reScanLog = queryDnByDelivery(reDelivery);

        if(StringUtils.isNotEmpty(shipToParty)){

            if(reScanLog == null) {
                ScanLog newScanLog = new ScanLog();
                newScanLog.setDelivery(delivery);
                newScanLog.setDeliveryResponse(message);
                newScanLog.setPlant(plant);
                this.baseMapper.insert(newScanLog);

                headerService.insertHeaderAndDetail(message,newScanLog.getId());
            }

        }
    }


    @Override
    @Transactional
    public DetailScanVo getDnMessageByDelivery(String delivery) {
        List<Opinion> plantList = opinionService.getOpinions("Plant");
        List<String> plants = plantList.stream().map(Opinion::getName).collect(Collectors.toList());

        DetailScanVo detailScanVo = new DetailScanVo();
        // 根据DN 先去sms_can_log表去查询是否有记录，有记录直接解析，没有记录就去调SOAP接口
        String message = "";
        String plant = "";
        ScanLog scanLog = queryDnByDelivery(delivery);
        List<Scan> scanList = scanService.queryScanByDelivery(delivery);

        if(scanLog!=null){
            //有数据，直接获取
            message = scanLog.getDeliveryResponse();
            plant = XmlUtils.getTagContent(message,"<PLANT>", "</PLANT>");
        }else {
            //没有数据，调接口
            String soapMessage = SoapWsUtils.getSoapMessage(delivery);
            try {
                message = SoapWsUtils.callWebService(SoapProperties.SOAPURL,soapMessage);

                plant = XmlUtils.getTagContent(message,"<PLANT>", "</PLANT>");
                //异步入库
                scanLogService.insertScanLog(message,delivery);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        detailScanVo.setPlant(plant);
        boolean isPlant = false;
        if(plants.contains(plant)){
            isPlant = true;
        }
        //进行解析
        detailScanVo.setDetailVoList(XmlUtils.resolverDetail(message,isPlant));
        detailScanVo.setType(isPlant);
        //判断是否有sap数据
        String reDelivery = XmlUtils.getTagContent(message, "<DELIVERY>", "</DELIVERY>");
        if(StringUtils.isEmpty(reDelivery)){
            detailScanVo.setStatus("3");  //没有sap返回记录
        }else{
            if(CollectionUtils.isNotEmpty(scanList)){
                detailScanVo.setStatus("1");  //有扫描记录
            }else {
                detailScanVo.setStatus("2");  //没有扫描记录
            }
        }
        return detailScanVo;
    }



}
