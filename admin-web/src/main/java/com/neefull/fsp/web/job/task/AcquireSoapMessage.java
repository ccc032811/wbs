package com.neefull.fsp.web.job.task;

import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.qff.config.SendMailProperties;
import com.neefull.fsp.web.qff.config.SoapUrlProperties;
import com.neefull.fsp.web.qff.entity.Commodity;
import com.neefull.fsp.web.qff.entity.Recent;
import com.neefull.fsp.web.qff.entity.Refund;
import com.neefull.fsp.web.qff.service.ICommodityService;
import com.neefull.fsp.web.qff.service.IProcessService;
import com.neefull.fsp.web.qff.service.IRefundService;
import com.neefull.fsp.web.qff.utils.MailUtils;
import com.neefull.fsp.web.qff.utils.SapWsUtils;
import com.neefull.fsp.web.qff.utils.XmlUtils;
import com.neefull.fsp.web.system.entity.User;
import com.neefull.fsp.web.system.service.IUserService;
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
    @Autowired
    private SendMailProperties mailProperties;
    @Autowired
    private IUserService userService;

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

//        String fromDate = "2019-05-01";
//        String toDate = "2019-05-31";

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
            List<Refund> refundList = new ArrayList<>();
            List<Commodity> commodityList = new ArrayList<>();
            String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

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
                        Boolean rstart = false;
                        StringBuilder alteration = new StringBuilder();

                        if(!refund.getPlant().equals(isRefund.getPlant())){
                            alteration.append("Plant工厂: " +date+ "   由"+isRefund.getPlant()+"变更为"+refund.getPlant()+" 。  ");
                            rstart = true;
                        }
                        if(!refund.getkMater().equals(isRefund.getkMater())){
                            alteration.append("KDLMaterial物料: " +date+ "   由"+isRefund.getkMater()+"变更为"+refund.getkMater()+" 。  ");
                            rstart = true;
                        }
                        if(!refund.getkBatch().equals(isRefund.getkBatch())){
                            alteration.append("康德乐SAP批次: " +date+ "   由"+isRefund.getkBatch()+"变更为"+refund.getkBatch()+" 。  ");
                            rstart = true;
                        }
                        if(!refund.getrMater().equals(isRefund.getrMater())){
                            alteration.append("罗氏物料号: " +date+ "   由"+isRefund.getrMater()+"变更为"+refund.getrMater()+" 。  ");
                            rstart = true;
                        }
                        if(!refund.getrBatch().equals(isRefund.getrBatch())){
                            alteration.append("罗氏批号: " +date+ "   由"+isRefund.getrBatch()+"变更为"+refund.getrBatch()+" 。  ");
                            rstart = true;
                        }
                        if(!refund.getManuDate().equals(isRefund.getManuDate())){
                            alteration.append("生产日期: " +date+ "   由"+isRefund.getManuDate()+"变更为"+refund.getManuDate()+" 。  ");
                            rstart = true;
                        }
                        if(!refund.getExpiryDate().equals(isRefund.getExpiryDate())){
                            alteration.append("有效期: " +date+ "   由"+isRefund.getExpiryDate()+"变更为"+refund.getExpiryDate()+" 。  ");
                            rstart = true;
                        }
                        if(!refund.getQuarantine().equals(isRefund.getQuarantine())){
                            alteration.append("异常总数: " +date+ "   由"+isRefund.getQuarantine()+"变更为"+refund.getQuarantine()+" 。  ");
                            rstart = true;
                        }
                        if(!refund.getGetRemark().equals(isRefund.getGetRemark())){
                            alteration.append("Remark箱号: " +date+ "   由"+isRefund.getGetRemark()+"变更为"+refund.getGetRemark()+" 。  ");
                            rstart = true;
                        }
                        if(!refund.getClassify().equals(isRefund.getClassify())){
                            alteration.append("产品分类: " +date+ "   由"+isRefund.getClassify()+"变更为"+refund.getClassify()+" 。  ");
                            rstart = true;
                        }
                        if(!refund.getRegister().equals(isRefund.getRegister())){
                            alteration.append("注册证号: " +date+ "   由"+isRefund.getRegister()+"变更为"+refund.getRegister()+" 。  ");
                            rstart = true;
                        }
                        if(rstart == true){
                            //重新发起流程
                            //查询流程是否存在
                            Boolean isExist = processService.queryProcessByKey(isRefund);
                            if(isExist){
                                processService.deleteInstance(isRefund);
                            }
                            refund.setId(isRefund.getId());
                            refund.setAlteration(alteration.toString());
                            refundService.editRefund(refund);

                            processService.commitProcess(refund,getCurrentUser());
                        }

                    }

                    refundList.add(refund);
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
                        Boolean rstart = false;
                        StringBuilder alteration = new StringBuilder();

                        if(!commodity.getPlant().equals(isCommodity.getPlant())){
                            alteration.append("Plant工厂: " +date+ "   由"+isCommodity.getPlant()+"变更为"+commodity.getPlant()+" 。  ");
                            rstart = true;
                        }
                        if(!commodity.getkMater().equals(isCommodity.getkMater())){
                            alteration.append("KDLMaterial物料: " +date+ "   由"+isCommodity.getkMater()+"变更为"+commodity.getkMater()+" 。  ");
                            rstart = true;
                        }
                        if(!commodity.getkBatch().equals(isCommodity.getkBatch())){
                            alteration.append("康德乐SAP批次: " +date+ "   由"+isCommodity.getkBatch()+"变更为"+commodity.getkBatch()+" 。  ");
                            rstart = true;
                        }
                        if(!commodity.getrMater().equals(isCommodity.getrMater())){
                            alteration.append("罗氏物料号: " +date+ "   由"+isCommodity.getrMater()+"变更为"+commodity.getrMater()+" 。  ");
                            rstart = true;
                        }
                        if(!commodity.getrBatch().equals(isCommodity.getrBatch())){
                            alteration.append("罗氏批号: " +date+ "   由"+isCommodity.getrBatch()+"变更为"+commodity.getrBatch()+" 。  ");
                            rstart = true;
                        }
                        if(!commodity.getManuDate().equals(isCommodity.getManuDate())){
                            alteration.append("生产日期: " +date+ "   由"+isCommodity.getManuDate()+"变更为"+commodity.getManuDate()+" 。  ");
                            rstart = true;
                        }
                        if(!commodity.getExpiryDate().equals(isCommodity.getExpiryDate())){
                            alteration.append("有效期: " +date+ "   由"+isCommodity.getExpiryDate()+"变更为"+commodity.getExpiryDate()+" 。  ");
                            rstart = true;
                        }
                        if(!commodity.getQuarantine().equals(isCommodity.getQuarantine())){
                            alteration.append("异常总数: " +date+ "   由"+isCommodity.getQuarantine()+"变更为"+commodity.getQuarantine()+" 。  ");
                            rstart = true;
                        }
                        if(!commodity.getGetRemark().equals(isCommodity.getGetRemark())){
                            alteration.append("Remark箱号: " +date+ "   由"+isCommodity.getGetRemark()+"变更为"+commodity.getGetRemark()+" 。  ");
                            rstart = true;
                        }
                        if(!commodity.getClassify().equals(isCommodity.getClassify())){
                            alteration.append("产品分类: " +date+ "   由"+isCommodity.getClassify()+"变更为"+commodity.getClassify()+" 。  ");
                            rstart = true;
                        }
                        if(!commodity.getRegister().equals(isCommodity.getRegister())){
                            alteration.append("注册证号: " +date+ "   由"+isCommodity.getRegister()+"变更为"+commodity.getRegister()+" 。  ");
                            rstart = true;
                        }
                        if(rstart == true){
                            //重新发起流程
                            //查询流程是否存在
                            Boolean isExist = processService.queryProcessByKey(isCommodity);
                            if(isExist){
                                processService.deleteInstance(isCommodity);
                            }
                            commodity.setId(isCommodity.getId());
                            commodity.setAlteration(alteration.toString());
                            commodityService.editCommodity(commodity);

                            processService.commitProcess(commodity,getCurrentUser());
                        }
                    }
                    commodityList.add(commodity);
                }
            }


            //对返回的数据附件进行存储


            //发送邮件
            //拼接文件内容
            StringBuilder content=new StringBuilder("<html><head></head><body><h3>您好。当前从SAP系统获取数据如下：</h3>");
            content.append("<tr><h3>具体详情如下表所示:</h3></tr>");
            content.append("<table border='5' style='border:solid 1px #000;font-size=10px;'>");
            content.append("<tr style='background-color: #00A1DD'><td>运输单号</td>" +
                    "<td>QFF编号</td><td>Plant工厂</td><td>KDL Material物料</td>" +
                    "<td>康德乐SAP 批次</td><td>罗氏物料号</td><td>药厂物料号</td><td>罗氏批号</td><td>生产日期</td>" +
                    "<td>有效期</td><td>异常总数</td><td>Remark箱号/备注</td></tr>");
            for (Refund refund : refundList) {

                content.append("<tr><td>"+refund.getTransport()+"</td><td>"+refund.getNumber()+"</td>" +
                        "<td>"+refund.getPlant()+"</td><td>"+refund.getkMater()+"</td>" +
                        "<td>"+refund.getkBatch()+"</td><td>"+refund.getrMater()+"</td>" +
                        "<td>"+" "+"</td><td>"+refund.getrBatch()+"</td><td>"+refund.getManuDate()+"</td>" +
                        "<td>"+refund.getExpiryDate()+"</td><td>"+refund.getQuarantine()+"</td><td>"+refund.getRemark()+"</td></tr>");
            }
            for (Commodity commodity : commodityList) {
                content.append("<tr><td>"+commodity.getTransport()+"</td><td>"+commodity.getNumber()+"</td>" +
                        "<td>"+commodity.getPlant()+"</td><td>"+commodity.getkMater()+"</td>" +
                        "<td>"+commodity.getkBatch()+"</td><td>"+commodity.getrMater()+"</td>" +
                        "<td>"+commodity.getpMater()+"</td><td>"+commodity.getrBatch()+"</td><td>"+commodity.getManuDate()+"</td>" +
                        "<td>"+commodity.getExpiryDate()+"</td><td>"+commodity.getQuarantine()+"</td><td>"+commodity.getRemark()+"</td></tr>");
            }
            content.append("</table>");
            content.append("</body></html>");

            String text = content.toString();
            //查询收件人
            List<User> userList = userService.findUserByRoleId(86);
            List<String> userMails = new ArrayList<>();
            for (User user : userList) {
                userMails.add(user.getEmail());
            }
            String[] mails = userMails.toArray(new String[0]);

            //发送邮件
            MailUtils.sendMail(text,mailProperties,mails);

        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }

}
