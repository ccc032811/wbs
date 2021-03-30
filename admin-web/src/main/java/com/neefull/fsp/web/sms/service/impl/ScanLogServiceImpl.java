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
//                message = "<soap-env:Envelope\n" +
//                        "    xmlns:soap-env=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
//                        "    <soap-env:Header/>\n" +
//                        "    <soap-env:Body>\n" +
//                        "        <n0:ZCHN_SD_GET_DN_INFOResponse\n" +
//                        "            xmlns:n0=\"urn:sap-com:document:sap:rfc:functions\">\n" +
//                        "            <E_DELIVERY_HEAD>\n" +
//                        "                <DELIVERY>8005205860</DELIVERY>\n" +
//                        "                <SOLD_TO_PARTY>0003030076</SOLD_TO_PARTY>\n" +
//                        "                <SOLD_TO_PARTY_NAME>国药控股沈阳有限公司</SOLD_TO_PARTY_NAME>\n" +
//                        "                <SHIP_TO_PARTY>0004009073</SHIP_TO_PARTY>\n" +
//                        "                <SHIP_TO_PARTY_NAME>国药控股沈阳有限公司</SHIP_TO_PARTY_NAME>\n" +
//                        "                <SHIP_TO_ADDRESS>沈阳市苏家屯区雪莲街158-2号</SHIP_TO_ADDRESS>\n" +
//                        "                <SALES_ORDER>2201884966</SALES_ORDER>\n" +
//                        "                <ROCHE_DELIVERY>6975018681</ROCHE_DELIVERY>\n" +
//                        "                <ROCHE_SHIP_TO_PARTY>38001301</ROCHE_SHIP_TO_PARTY>\n" +
//                        "                <ROCHE_SHIP_TO_PARTY_NAME>国药控股沈阳有限公司</ROCHE_SHIP_TO_PARTY_NAME>\n" +
//                        "                <ROCHE_SALES_ORDER>6930019678</ROCHE_SALES_ORDER>\n" +
//                        "                <ROCHE_CUSTOMER_ORDER/>\n" +
//                        "            </E_DELIVERY_HEAD>\n" +
//                        "            <T_DELIVERY_ITEM>\n" +
//                        "                <item>\n" +
//                        "                    <DELIVERY_ITEM>000010</DELIVERY_ITEM>\n" +
//                        "                    <ROCHE_DELIVERY_ITEM>900001</ROCHE_DELIVERY_ITEM>\n" +
//                        "                    <ROCHE_DELIVERY_ITEM_COUNT>3</ROCHE_DELIVERY_ITEM_COUNT>\n" +
//                        "                    <MATERIAL>000000000030437167</MATERIAL>\n" +
//                        "                    <ROCHE_MATERIAL>10174222</ROCHE_MATERIAL>\n" +
//                        "                    <MATERIAL_DESCRIPTION>贝伐珠单抗注射液(安维汀)100mg/4ml 1</MATERIAL_DESCRIPTION>\n" +
//                        "                    <MATERIAL_DESCRIPTION_EN>Avastin Vial 100mg/4ml 1</MATERIAL_DESCRIPTION_EN>\n" +
//                        "                    <ROCHE_MATERIAL_DESCRIPTION>贝伐珠单抗注射液100mg(4ml)/瓶1瓶/盒</ROCHE_MATERIAL_DESCRIPTION>\n" +
//                        "                    <PLANT>T110</PLANT>\n" +
//                        "                    <BATCH>8001978260</BATCH>\n" +
//                        "                    <ROCHE_BATCH>H0246B01</ROCHE_BATCH>\n" +
//                        "                    <SERIAL_NUMBER/>\n" +
//                        "                    <QUANTITY>1575.0</QUANTITY>\n" +
//                        "                    <UOM>EA</UOM>\n" +
//                        "                    <ROCHE_UOM>EA</ROCHE_UOM>\n" +
//                        "                    <EXPIRY_DATE>20211231</EXPIRY_DATE>\n" +
//                        "                    <IF_BATCH_MANAGEMENT>X</IF_BATCH_MANAGEMENT>\n" +
//                        "                    <IF_SERIAL_NUMBER_MANAGEMENT/>\n" +
//                        "                    <IF_EXPIRY_DATE_MANAGEMENT>X</IF_EXPIRY_DATE_MANAGEMENT>\n" +
//                        "                    <SAVE_MODE>L1</SAVE_MODE>\n" +
//                        "                    <SAVE_MODE_DESCRIPTION>冷藏2至8度</SAVE_MODE_DESCRIPTION>\n" +
//                        "                </item>\n" +
//                        "                <item>\n" +
//                        "                    <DELIVERY_ITEM>000020</DELIVERY_ITEM>\n" +
//                        "                    <ROCHE_DELIVERY_ITEM>900002</ROCHE_DELIVERY_ITEM>\n" +
//                        "                    <ROCHE_DELIVERY_ITEM_COUNT>3</ROCHE_DELIVERY_ITEM_COUNT>\n" +
//                        "                    <MATERIAL>000000000030472920</MATERIAL>\n" +
//                        "                    <ROCHE_MATERIAL>10194047</ROCHE_MATERIAL>\n" +
//                        "                    <MATERIAL_DESCRIPTION>帕妥珠单抗注射液(帕捷特)420MG/14ML 1</MATERIAL_DESCRIPTION>\n" +
//                        "                    <MATERIAL_DESCRIPTION_EN>Perjeta Vial 420mg/14ml 1 CN</MATERIAL_DESCRIPTION_EN>\n" +
//                        "                    <ROCHE_MATERIAL_DESCRIPTION>帕妥珠单抗注射液420 mg (14ml) /瓶西林瓶，1瓶/盒</ROCHE_MATERIAL_DESCRIPTION>\n" +
//                        "                    <PLANT>T110</PLANT>\n" +
//                        "                    <BATCH>8001945756</BATCH>\n" +
//                        "                    <ROCHE_BATCH>H0414B03</ROCHE_BATCH>\n" +
//                        "                    <SERIAL_NUMBER/>\n" +
//                        "                    <QUANTITY>210.0</QUANTITY>\n" +
//                        "                    <UOM>EA</UOM>\n" +
//                        "                    <ROCHE_UOM>EA</ROCHE_UOM>\n" +
//                        "                    <EXPIRY_DATE>20220131</EXPIRY_DATE>\n" +
//                        "                    <IF_BATCH_MANAGEMENT>X</IF_BATCH_MANAGEMENT>\n" +
//                        "                    <IF_SERIAL_NUMBER_MANAGEMENT/>\n" +
//                        "                    <IF_EXPIRY_DATE_MANAGEMENT>X</IF_EXPIRY_DATE_MANAGEMENT>\n" +
//                        "                    <SAVE_MODE>L1</SAVE_MODE>\n" +
//                        "                    <SAVE_MODE_DESCRIPTION>冷藏2至8度</SAVE_MODE_DESCRIPTION>\n" +
//                        "                </item>\n" +
//                        "                <item>\n" +
//                        "                    <DELIVERY_ITEM>000030</DELIVERY_ITEM>\n" +
//                        "                    <ROCHE_DELIVERY_ITEM>900003</ROCHE_DELIVERY_ITEM>\n" +
//                        "                    <ROCHE_DELIVERY_ITEM_COUNT>3</ROCHE_DELIVERY_ITEM_COUNT>\n" +
//                        "                    <MATERIAL>000000000030442140</MATERIAL>\n" +
//                        "                    <ROCHE_MATERIAL>10188880</ROCHE_MATERIAL>\n" +
//                        "                    <MATERIAL_DESCRIPTION>托珠单抗注射液(雅美罗) 80mg/4ml 1</MATERIAL_DESCRIPTION>\n" +
//                        "                    <MATERIAL_DESCRIPTION_EN>Actemra Vial 80mg/4ml 1</MATERIAL_DESCRIPTION_EN>\n" +
//                        "                    <ROCHE_MATERIAL_DESCRIPTION>托珠单抗注射液80mg/4ml1瓶/盒</ROCHE_MATERIAL_DESCRIPTION>\n" +
//                        "                    <PLANT>T110</PLANT>\n" +
//                        "                    <BATCH>8001972255</BATCH>\n" +
//                        "                    <ROCHE_BATCH>B2106B01</ROCHE_BATCH>\n" +
//                        "                    <SERIAL_NUMBER/>\n" +
//                        "                    <QUANTITY>315.0</QUANTITY>\n" +
//                        "                    <UOM>EA</UOM>\n" +
//                        "                    <ROCHE_UOM>EA</ROCHE_UOM>\n" +
//                        "                    <EXPIRY_DATE>20220831</EXPIRY_DATE>\n" +
//                        "                    <IF_BATCH_MANAGEMENT>X</IF_BATCH_MANAGEMENT>\n" +
//                        "                    <IF_SERIAL_NUMBER_MANAGEMENT/>\n" +
//                        "                    <IF_EXPIRY_DATE_MANAGEMENT>X</IF_EXPIRY_DATE_MANAGEMENT>\n" +
//                        "                    <SAVE_MODE>L1</SAVE_MODE>\n" +
//                        "                    <SAVE_MODE_DESCRIPTION>冷藏2至8度</SAVE_MODE_DESCRIPTION>\n" +
//                        "                </item>\n" +
//                        "            </T_DELIVERY_ITEM>\n" +
//                        "            <T_MSG/>\n" +
//                        "        </n0:ZCHN_SD_GET_DN_INFOResponse>\n" +
//                        "    </soap-env:Body>\n" +
//                        "</soap-env:Envelope>";
                plant = XmlUtils.getTagContent(message,"<PLANT>", "</PLANT>");

//                String soldToParty = XmlUtils.getTagContent(message, "<SOLD_TO_PARTY>", "</SOLD_TO_PARTY>");
//                String shipToParty = XmlUtils.getTagContent(message, "<SHIP_TO_PARTY>", "</SHIP_TO_PARTY>");
//                String reDelivery = XmlUtils.getTagContent(message, "<DELIVERY>", "</DELIVERY>");
//
//                ScanLog reScanLog = queryDnByDelivery(reDelivery);
//
//                if(StringUtils.isNotEmpty(soldToParty)&&StringUtils.isNotEmpty(shipToParty)){
//                    if(reScanLog == null) {
//                        ScanLog newScanLog = new ScanLog();
//                        newScanLog.setDelivery(delivery);
//                        newScanLog.setDeliveryResponse(message);
//                        newScanLog.setPlant(plant);
//                        this.baseMapper.insert(newScanLog);
//
//                        headerService.insertHeaderAndDetail(message,newScanLog.getId());
//                    }
//                }
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
