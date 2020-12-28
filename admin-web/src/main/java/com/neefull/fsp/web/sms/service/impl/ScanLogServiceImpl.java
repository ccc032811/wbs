package com.neefull.fsp.web.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.sms.entity.Scan;
import com.neefull.fsp.web.sms.entity.ScanLog;
import com.neefull.fsp.web.sms.entity.vo.DetailScanVo;
import com.neefull.fsp.web.sms.entity.vo.DetailVo;
import com.neefull.fsp.web.sms.mapper.ScanLogMapper;
import com.neefull.fsp.web.sms.service.IScanLogService;
import com.neefull.fsp.web.sms.service.IScanService;
import com.neefull.fsp.web.sms.utils.ScanComment;
import com.neefull.fsp.web.sms.utils.SoapProperties;
import com.neefull.fsp.web.sms.utils.SoapWsUtils;
import com.neefull.fsp.web.sms.utils.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/26  14:34
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScanLogServiceImpl extends ServiceImpl<ScanLogMapper, ScanLog> implements IScanLogService {


    @Autowired
    private IScanService scanService;


    @Override
    public ScanLog queryDnByDelivery(String delivery) {
        return this.baseMapper.selectOne(new QueryWrapper<ScanLog>().eq("delivery",delivery));
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, String status) {
        this.baseMapper.updateStatus(id,status);
    }



//    @Override
//    public List<ScanLog> selectScanLogInsert() {
//        return this.baseMapper.selectList(new QueryWrapper<ScanLog>().eq("status", ScanComment.STATUS_ONE));
//
//    }


//    @Override
//    @Transactional
//    public void updateScanLogStatus(List<ScanLog> headerList) {
//        for (ScanLog scanLog : headerList) {
//            this.baseMapper.updateStatus(scanLog.getId(),ScanComment.STATUS_TWO);
//        }
//    }


    @Override
    public IPage<ScanLog> queryScanLogList(ScanLog scanLog) {
        ScanLog singleScanLog = (ScanLog) ScanComment.containPlant(scanLog);
        IPage<ScanLog> scanLogPage = new Page<ScanLog>(singleScanLog.getPageNum(), singleScanLog.getPageSize());
        return this.baseMapper.getPageScanLog(scanLogPage,singleScanLog);

    }


    @Override
    @Transactional
    public DetailScanVo getDnMessageByDelivery(String delivery) {
        DetailScanVo detailScanVo = new DetailScanVo();

        String message = "";
        ScanLog scanLog = queryDnByDelivery(delivery);
        List<Scan> scanList = scanService.queryScanByDelivery(delivery);

        if(scanLog!=null){
            message = scanLog.getDeliveryResponse();
        }else {
            String soapMessage = SoapWsUtils.getSoapMessage(delivery);
            try {
//                message = "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
//                        "   <env:Header/>\n" +
//                        "   <env:Body>\n" +
//                        "      <n0:ZCHN_SD_GET_DN_INFOResponse xmlns:n0=\"urn:sap-com:document:sap:rfc:functions\">\n" +
//                        "         <E_DELIVERY_HEAD>\n" +
//                        "            <DELIVERY>8000000777</DELIVERY>\n" +
//                        "            <SOLD_TO_PARTY/>\n" +
//                        "            <SOLD_TO_PARTY_NAME/>\n" +
//                        "            <SHIP_TO_PARTY>0002014501</SHIP_TO_PARTY>\n" +
//                        "            <SHIP_TO_PARTY_NAME>华领医药技术(上海)有限公司</SHIP_TO_PARTY_NAME>\n" +
//                        "            <SHIP_TO_ADDRESS/>\n" +
//                        "            <SALES_ORDER/>\n" +
//                        "            <ROCHE_DELIVERY/>\n" +
//                        "            <ROCHE_SHIP_TO_PARTY/>\n" +
//                        "            <ROCHE_SHIP_TO_PARTY_NAME>华领医药技术(上海)有限公司</ROCHE_SHIP_TO_PARTY_NAME>\n" +
//                        "            <ROCHE_SALES_ORDER/>\n" +
//                        "            <ROCHE_CUSTOMER_ORDER/>\n" +
//                        "         </E_DELIVERY_HEAD>\n" +
//                        "         <T_DELIVERY_ITEM>\n" +
//                        "            <item>\n" +
//                        "               <DELIVERY_ITEM>000010</DELIVERY_ITEM>\n" +
//                        "               <ROCHE_DELIVERY_ITEM/>\n" +
//                        "               <ROCHE_DELIVERY_ITEM_COUNT>1</ROCHE_DELIVERY_ITEM_COUNT>\n" +
//                        "               <MATERIAL>000000000021117957</MATERIAL>\n" +
//                        "               <ROCHE_MATERIAL/>\n" +
//                        "               <MATERIAL_DESCRIPTION>拜新同 硝苯地平控释片 30mg*7片/盒</MATERIAL_DESCRIPTION>\n" +
//                        "               <MATERIAL_DESCRIPTION_EN/>\n" +
//                        "               <ROCHE_MATERIAL_DESCRIPTION>硝苯地平控释片30mg7片/盒</ROCHE_MATERIAL_DESCRIPTION>\n" +
//                        "               <PLANT>D005</PLANT>\n" +
//                        "               <BATCH>0000001037</BATCH>\n" +
//                        "               <ROCHE_BATCH>N/A</ROCHE_BATCH>\n" +
//                        "               <SERIAL_NUMBER/>\n" +
//                        "               <QUANTITY>11.0</QUANTITY>\n" +
//                        "               <UOM>PAC</UOM>\n" +
//                        "               <ROCHE_UOM>PAC</ROCHE_UOM>\n" +
//                        "               <EXPIRY_DATE>20200129</EXPIRY_DATE>\n" +
//                        "               <IF_BATCH_MANAGEMENT>X</IF_BATCH_MANAGEMENT>\n" +
//                        "               <IF_SERIAL_NUMBER_MANAGEMENT/>\n" +
//                        "               <IF_EXPIRY_DATE_MANAGEMENT>X</IF_EXPIRY_DATE_MANAGEMENT>\n" +
//                        "               <SAVE_MODE>W1</SAVE_MODE>\n" +
//                        "               <SAVE_MODE_DESCRIPTION>温控15至25度</SAVE_MODE_DESCRIPTION>\n" +
//                        "            </item>\n" +
//                        "            <item>\n" +
//                        "               <DELIVERY_ITEM>000011</DELIVERY_ITEM>\n" +
//                        "               <ROCHE_DELIVERY_ITEM/>\n" +
//                        "               <ROCHE_DELIVERY_ITEM_COUNT>1</ROCHE_DELIVERY_ITEM_COUNT>\n" +
//                        "               <MATERIAL>000000000021117958</MATERIAL>\n" +
//                        "               <ROCHE_MATERIAL/>\n" +
//                        "               <MATERIAL_DESCRIPTION>拜新同 硝苯地平控释片 30mg*7片/盒</MATERIAL_DESCRIPTION>\n" +
//                        "               <MATERIAL_DESCRIPTION_EN/>\n" +
//                        "               <ROCHE_MATERIAL_DESCRIPTION>硝苯地平控释片30mg7片/盒</ROCHE_MATERIAL_DESCRIPTION>\n" +
//                        "               <PLANT>D005</PLANT>\n" +
//                        "               <BATCH>0000001038</BATCH>\n" +
//                        "               <ROCHE_BATCH>N/A</ROCHE_BATCH>\n" +
//                        "               <SERIAL_NUMBER/>\n" +
//                        "               <QUANTITY>15.58</QUANTITY>\n" +
//                        "               <UOM>PAC</UOM>\n" +
//                        "               <ROCHE_UOM>PAC</ROCHE_UOM>\n" +
//                        "               <EXPIRY_DATE>20200129</EXPIRY_DATE>\n" +
//                        "               <IF_BATCH_MANAGEMENT>X</IF_BATCH_MANAGEMENT>\n" +
//                        "               <IF_SERIAL_NUMBER_MANAGEMENT/>\n" +
//                        "               <IF_EXPIRY_DATE_MANAGEMENT>X</IF_EXPIRY_DATE_MANAGEMENT>\n" +
//                        "               <SAVE_MODE>W1</SAVE_MODE>\n" +
//                        "               <SAVE_MODE_DESCRIPTION>温控15至25度</SAVE_MODE_DESCRIPTION>\n" +
//                        "            </item>\n" +
//                        "           <item>\n" +
//                        "               <DELIVERY_ITEM>000012</DELIVERY_ITEM>\n" +
//                        "               <ROCHE_DELIVERY_ITEM/>\n" +
//                        "               <ROCHE_DELIVERY_ITEM_COUNT>1</ROCHE_DELIVERY_ITEM_COUNT>\n" +
//                        "               <MATERIAL>000000000021117959</MATERIAL>\n" +
//                        "               <ROCHE_MATERIAL/>\n" +
//                        "               <MATERIAL_DESCRIPTION>拜新同 硝苯地平控释片 30mg*7片/盒</MATERIAL_DESCRIPTION>\n" +
//                        "               <MATERIAL_DESCRIPTION_EN/>\n" +
//                        "               <ROCHE_MATERIAL_DESCRIPTION>硝苯地平控释片30mg7片/盒</ROCHE_MATERIAL_DESCRIPTION>\n" +
//                        "               <PLANT>D005</PLANT>\n" +
//                        "               <BATCH>0000001039</BATCH>\n" +
//                        "               <ROCHE_BATCH>N/A</ROCHE_BATCH>\n" +
//                        "               <SERIAL_NUMBER/>\n" +
//                        "               <QUANTITY>12.58</QUANTITY>\n" +
//                        "               <UOM>PAC</UOM>\n" +
//                        "               <ROCHE_UOM>PAC</ROCHE_UOM>\n" +
//                        "               <EXPIRY_DATE>20200129</EXPIRY_DATE>\n" +
//                        "               <IF_BATCH_MANAGEMENT>X</IF_BATCH_MANAGEMENT>\n" +
//                        "               <IF_SERIAL_NUMBER_MANAGEMENT/>\n" +
//                        "               <IF_EXPIRY_DATE_MANAGEMENT>X</IF_EXPIRY_DATE_MANAGEMENT>\n" +
//                        "               <SAVE_MODE>W1</SAVE_MODE>\n" +
//                        "               <SAVE_MODE_DESCRIPTION>温控15至25度</SAVE_MODE_DESCRIPTION>\n" +
//                        "            </item>\n" +
//                        "           <item>\n" +
//                        "               <DELIVERY_ITEM>000013</DELIVERY_ITEM>\n" +
//                        "               <ROCHE_DELIVERY_ITEM/>\n" +
//                        "               <ROCHE_DELIVERY_ITEM_COUNT>1</ROCHE_DELIVERY_ITEM_COUNT>\n" +
//                        "               <MATERIAL>000000000021117960</MATERIAL>\n" +
//                        "               <ROCHE_MATERIAL/>\n" +
//                        "               <MATERIAL_DESCRIPTION>拜新同 硝苯地平控释片 30mg*7片/盒</MATERIAL_DESCRIPTION>\n" +
//                        "               <MATERIAL_DESCRIPTION_EN/>\n" +
//                        "               <ROCHE_MATERIAL_DESCRIPTION>硝苯地平控释片30mg7片/盒</ROCHE_MATERIAL_DESCRIPTION>\n" +
//                        "               <PLANT>D005</PLANT>\n" +
//                        "               <BATCH>0000001060</BATCH>\n" +
//                        "               <ROCHE_BATCH>N/A</ROCHE_BATCH>\n" +
//                        "               <SERIAL_NUMBER/>\n" +
//                        "               <QUANTITY>892.58</QUANTITY>\n" +
//                        "               <UOM>PAC</UOM>\n" +
//                        "               <ROCHE_UOM>PAC</ROCHE_UOM>\n" +
//                        "               <EXPIRY_DATE>20200129</EXPIRY_DATE>\n" +
//                        "               <IF_BATCH_MANAGEMENT>X</IF_BATCH_MANAGEMENT>\n" +
//                        "               <IF_SERIAL_NUMBER_MANAGEMENT/>\n" +
//                        "               <IF_EXPIRY_DATE_MANAGEMENT>X</IF_EXPIRY_DATE_MANAGEMENT>\n" +
//                        "               <SAVE_MODE>W1</SAVE_MODE>\n" +
//                        "               <SAVE_MODE_DESCRIPTION>温控15至25度</SAVE_MODE_DESCRIPTION>\n" +
//                        "            </item>\n" +
//                        "           <item>\n" +
//                        "               <DELIVERY_ITEM>000014</DELIVERY_ITEM>\n" +
//                        "               <ROCHE_DELIVERY_ITEM/>\n" +
//                        "               <ROCHE_DELIVERY_ITEM_COUNT>1</ROCHE_DELIVERY_ITEM_COUNT>\n" +
//                        "               <MATERIAL>000000000021117961</MATERIAL>\n" +
//                        "               <ROCHE_MATERIAL/>\n" +
//                        "               <MATERIAL_DESCRIPTION>拜新同 硝苯地平控释片 30mg*7片/盒</MATERIAL_DESCRIPTION>\n" +
//                        "               <MATERIAL_DESCRIPTION_EN/>\n" +
//                        "               <ROCHE_MATERIAL_DESCRIPTION>硝苯地平控释片30mg7片/盒</ROCHE_MATERIAL_DESCRIPTION>\n" +
//                        "               <PLANT>D005</PLANT>\n" +
//                        "               <BATCH>0000001061</BATCH>\n" +
//                        "               <ROCHE_BATCH>N/A</ROCHE_BATCH>\n" +
//                        "               <SERIAL_NUMBER/>\n" +
//                        "               <QUANTITY>92.58</QUANTITY>\n" +
//                        "               <UOM>PAC</UOM>\n" +
//                        "               <ROCHE_UOM>PAC</ROCHE_UOM>\n" +
//                        "               <EXPIRY_DATE>20200129</EXPIRY_DATE>\n" +
//                        "               <IF_BATCH_MANAGEMENT>X</IF_BATCH_MANAGEMENT>\n" +
//                        "               <IF_SERIAL_NUMBER_MANAGEMENT/>\n" +
//                        "               <IF_EXPIRY_DATE_MANAGEMENT>X</IF_EXPIRY_DATE_MANAGEMENT>\n" +
//                        "               <SAVE_MODE>W1</SAVE_MODE>\n" +
//                        "               <SAVE_MODE_DESCRIPTION>温控15至25度</SAVE_MODE_DESCRIPTION>\n" +
//                        "            </item>\n" +
//                        "\n" +
//                        "           <item>\n" +
//                        "               <DELIVERY_ITEM>000015</DELIVERY_ITEM>\n" +
//                        "               <ROCHE_DELIVERY_ITEM/>\n" +
//                        "               <ROCHE_DELIVERY_ITEM_COUNT>1</ROCHE_DELIVERY_ITEM_COUNT>\n" +
//                        "               <MATERIAL>000000000021117962</MATERIAL>\n" +
//                        "               <ROCHE_MATERIAL/>\n" +
//                        "               <MATERIAL_DESCRIPTION>拜新同 硝苯地平控释片 30mg*7片/盒</MATERIAL_DESCRIPTION>\n" +
//                        "               <MATERIAL_DESCRIPTION_EN/>\n" +
//                        "               <ROCHE_MATERIAL_DESCRIPTION>硝苯地平控释片30mg7片/盒</ROCHE_MATERIAL_DESCRIPTION>\n" +
//                        "               <PLANT>D005</PLANT>\n" +
//                        "               <BATCH>0000001062</BATCH>\n" +
//                        "               <ROCHE_BATCH>N/A</ROCHE_BATCH>\n" +
//                        "               <SERIAL_NUMBER/>\n" +
//                        "               <QUANTITY>92.58</QUANTITY>\n" +
//                        "               <UOM>PAC</UOM>\n" +
//                        "               <ROCHE_UOM>PAC</ROCHE_UOM>\n" +
//                        "               <EXPIRY_DATE>20200129</EXPIRY_DATE>\n" +
//                        "               <IF_BATCH_MANAGEMENT>X</IF_BATCH_MANAGEMENT>\n" +
//                        "               <IF_SERIAL_NUMBER_MANAGEMENT/>\n" +
//                        "               <IF_EXPIRY_DATE_MANAGEMENT>X</IF_EXPIRY_DATE_MANAGEMENT>\n" +
//                        "               <SAVE_MODE>W1</SAVE_MODE>\n" +
//                        "               <SAVE_MODE_DESCRIPTION>温控15至25度</SAVE_MODE_DESCRIPTION>\n" +
//                        "            </item>\n" +
//                        "\n" +
//                        "           <item>\n" +
//                        "               <DELIVERY_ITEM>000015</DELIVERY_ITEM>\n" +
//                        "               <ROCHE_DELIVERY_ITEM/>\n" +
//                        "               <ROCHE_DELIVERY_ITEM_COUNT>1</ROCHE_DELIVERY_ITEM_COUNT>\n" +
//                        "               <MATERIAL>000000000021117962</MATERIAL>\n" +
//                        "               <ROCHE_MATERIAL/>\n" +
//                        "               <MATERIAL_DESCRIPTION>拜新同 硝苯地平控释片 30mg*7片/盒</MATERIAL_DESCRIPTION>\n" +
//                        "               <MATERIAL_DESCRIPTION_EN/>\n" +
//                        "               <ROCHE_MATERIAL_DESCRIPTION>硝苯地平控释片30mg7片/盒</ROCHE_MATERIAL_DESCRIPTION>\n" +
//                        "               <PLANT>D005</PLANT>\n" +
//                        "               <BATCH>0000001062</BATCH>\n" +
//                        "               <ROCHE_BATCH>N/A</ROCHE_BATCH>\n" +
//                        "               <SERIAL_NUMBER/>\n" +
//                        "               <QUANTITY>22.58</QUANTITY>\n" +
//                        "               <UOM>PAC</UOM>\n" +
//                        "               <ROCHE_UOM>PAC</ROCHE_UOM>\n" +
//                        "               <EXPIRY_DATE>20200129</EXPIRY_DATE>\n" +
//                        "               <IF_BATCH_MANAGEMENT>X</IF_BATCH_MANAGEMENT>\n" +
//                        "               <IF_SERIAL_NUMBER_MANAGEMENT/>\n" +
//                        "               <IF_EXPIRY_DATE_MANAGEMENT>X</IF_EXPIRY_DATE_MANAGEMENT>\n" +
//                        "               <SAVE_MODE>W1</SAVE_MODE>\n" +
//                        "               <SAVE_MODE_DESCRIPTION>温控15至25度</SAVE_MODE_DESCRIPTION>\n" +
//                        "            </item>\n" +
//                        "         </T_DELIVERY_ITEM>\n" +
//                        "         <T_MSG>\n" +
//                        "            <item>\n" +
//                        "               <TYPE/>\n" +
//                        "               <MESSAGE/>\n" +
//                        "            </item>\n" +
//                        "         </T_MSG>\n" +
//                        "      </n0:ZCHN_SD_GET_DN_INFOResponse>\n" +
//                        "   </env:Body>\n" +
//                        "</env:Envelope>";
                message = SoapWsUtils.callWebService(SoapProperties.SOAPURL,soapMessage);
                ScanLog newScanLog = new ScanLog();
                newScanLog.setDelivery(delivery);
                newScanLog.setDeliveryResponse(message);
                newScanLog.setPlant(XmlUtils.getTagContent(message,"<PLANT>", "</PLANT>"));
                this.baseMapper.insert(newScanLog);

                scanService.insertHeaderAndDetail(message,newScanLog.getId());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        detailScanVo.setDetailVoList(XmlUtils.resolverDetail(message));
        if(CollectionUtils.isNotEmpty(scanList)){
            detailScanVo.setStatus("1");  //有扫描记录
        }else {
            detailScanVo.setStatus("2");  //没有扫描记录
        }
        return detailScanVo;
    }



}
