package com.neefull.fsp.web.job.task;

import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.qff.config.SoapUrlProperties;
import com.neefull.fsp.web.qff.entity.Commodity;
import com.neefull.fsp.web.qff.entity.Refund;
import com.neefull.fsp.web.qff.service.ICommodityService;
import com.neefull.fsp.web.qff.service.IProcessService;
import com.neefull.fsp.web.qff.service.IRefundService;
import com.neefull.fsp.web.qff.utils.MailUtils;
import com.neefull.fsp.web.qff.utils.SapWsUtils;
import com.neefull.fsp.web.qff.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/3/11  15:34
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AcquireSoapMessage extends BaseController {

    @Autowired
    private SoapUrlProperties properties;
    @Autowired
    private ICommodityService commodityService;
    @Autowired
    private IRefundService refundService;
    @Autowired
    private IProcessService processService;

    @Transactional
    public void getMessage(){

        log.info("*****************Execute query from SAP server.******************");
        long startTime = System.currentTimeMillis();
        //获取更新时间
        String fromDate = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        Date time = c.getTime();
        String toDate = DateFormatUtils.format(time, "yyyy-MM-dd");
        //构建请求报文
        String soapMessage = SapWsUtils.getSoapMessage(fromDate, toDate);
        log.info("请求报文为："+soapMessage);
        //进行请求 获取soap返回结果
        StringBuffer messageBuffer = null;
        try {
            messageBuffer = SapWsUtils.callWebService(properties.getSoapUrl(), soapMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("返回结果为："+messageBuffer.toString());
        long endTime = System.currentTimeMillis();
        log.info("响应时间: {}ms", (endTime - startTime));
        log.info("*****************Finish query from SAP server.******************");

        //对返回数据结果进行截取
        try {
            String message = XmlUtils.getTagContent(messageBuffer.toString(), "<ET_QFF>", "</ET_QFF>");
            String[] split = message.split("<item>");
            List<String> list = new ArrayList<>();
            for (String s : split) {
                if(StringUtils.isNotEmpty(s)){
                    String dom = s.split("</item>")[0];
                    list.add(dom);
                }
            }

            //将数据转换成对象存入数据库中
            for (String s : list) {
                String stage = XmlUtils.getTagContent(s, "<HERKUNFT>", "</HERKUNFT>");
                if(stage.equals("05")){
                    Refund refund = new Refund();
                    refund.setNumber(XmlUtils.getTagContent(s,"<QMNUM>","</QMNUM>"));
                    refund.setPlant(XmlUtils.getTagContent(s,"<MAWERK>","</MAWERK>"));
                    refund.setkMater(XmlUtils.getTagContent(s,"<MATNR>","</MATNR>"));
                    refund.setkBatch(XmlUtils.getTagContent(s,"<CHARG>","</CHARG>"));
                    refund.setrMater(XmlUtils.getTagContent(s,"<IDNLF>","</IDNLF>"));
                    refund.setrBatch(XmlUtils.getTagContent(s,"<LICHN>","</LICHN>"));
                    refund.setManuDate(XmlUtils.getTagContent(s,"<HSDAT>","</HSDAT>"));
                    refund.setExpiryDate(XmlUtils.getTagContent(s,"<VFDAT>","</VFDAT>"));
                    refund.setQuarantine(XmlUtils.getTagContent(s,"<MGEIG>","</MGEIG>"));
                    refund.setGetRemark(XmlUtils.getTagContent(s,"<QMTXT>","</QMTXT>"));
                    refund.setInitDate(XmlUtils.getTagContent(s,"<ERDAT>","</ERDAT>"));
                    refund.setClassify(XmlUtils.getTagContent(s,"<ZPROCLAS>","</ZPROCLAS>"));
                    refund.setRegister(XmlUtils.getTagContent(s,"<REGNO>","</REGNO>"));
                    refund.setType(stage);

                    //查询数据库是否有该条数据
                    Refund isRefund = refundService.queryRefundByNumber(refund.getNumber());
                    if(isRefund == null){
                        refundService.addRefund(refund);
                        processService.commitProcess(refund,getCurrentUser());
                    }else {
                        //对变化的字段进行记录

                        //重新发起流程
                        //查询流程是否存在
                        Boolean isExist = processService.queryProcessByKey(refund);
                        if(isExist){
                            processService.deleteInstance(refund);
                        }
                        processService.commitProcess(refund,getCurrentUser());
                    }
                }else {
                    Commodity commodity = new Commodity();
                    commodity.setNumber(XmlUtils.getTagContent(s,"<QMNUM>","</QMNUM>"));
                    commodity.setPlant(XmlUtils.getTagContent(s,"<MAWERK>","</MAWERK>"));
                    commodity.setkMater(XmlUtils.getTagContent(s,"<MATNR>","</MATNR>"));
                    commodity.setkBatch(XmlUtils.getTagContent(s,"<CHARG>","</CHARG>"));
                    commodity.setrMater(XmlUtils.getTagContent(s,"<IDNLF>","</IDNLF>"));
                    commodity.setIsDanger(XmlUtils.getTagContent(s,"<MSTAE>","</MSTAE>"));
                    commodity.setpMater(XmlUtils.getTagContent(s,"<BISMT>","</BISMT>"));
                    commodity.setrBatch(XmlUtils.getTagContent(s,"<LICHN>","</LICHN>"));
                    commodity.setManuDate(XmlUtils.getTagContent(s,"<HSDAT>","</HSDAT>"));
                    commodity.setExpiryDate(XmlUtils.getTagContent(s,"<VFDAT>","</VFDAT>"));
                    commodity.setQuarantine(XmlUtils.getTagContent(s,"<MGEIG>","</MGEIG>"));
                    commodity.setGetRemark(XmlUtils.getTagContent(s,"<QMTXT>","</QMTXT>"));
                    commodity.setInitDate(XmlUtils.getTagContent(s,"<ERDAT>","</ERDAT>"));
                    commodity.setClassify(XmlUtils.getTagContent(s,"<ZPROCLAS>","</ZPROCLAS>"));
                    commodity.setRegister(XmlUtils.getTagContent(s,"<REGNO>","</REGNO>"));
                    commodity.setTransport(XmlUtils.getTagContent(s,"<AWBNO>","</AWBNO>"));
                    commodity.setType(stage);
//                    commodity.setStage(XmlUtils.getTagContent(s,"<HERKUNFT>","</HERKUNFT>"));
                    if(stage.equals("01")){
                        commodity.setStage("到货");
                    }else if(stage.equals("09")){
                        commodity.setStage("养护");
                    }else if(stage.equals("10")||stage.equals("11")){
                        commodity.setStage("出库");
                    }else {
                        commodity.setStage("其他");
                    }

                    Commodity isCommodity = commodityService.queryCommodityByNumber(commodity.getNumber());
                    if(isCommodity ==null){
                        commodityService.addCommodity(commodity);
                        processService.commitProcess(commodity,getCurrentUser());
                    }else {
                        //对变化的字段进行记录


                        //重新发起流程
                        //查询流程是否存在
                        Boolean isExist = processService.queryProcessByKey(commodity);
                        if(isExist){
                            //流程存在  删除原先的流程
                            processService.deleteInstance(commodity);
                        }
                        //提交新流程
                        processService.commitProcess(commodity,getCurrentUser());
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //对返回的数据附件进行存储



        //发送邮件


    }

}
