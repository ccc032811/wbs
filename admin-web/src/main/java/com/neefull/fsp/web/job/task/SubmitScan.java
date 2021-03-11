package com.neefull.fsp.web.job.task;

import com.alibaba.fastjson.JSON;
import com.neefull.fsp.web.sms.entity.Header;
import com.neefull.fsp.web.sms.entity.Scan;
import com.neefull.fsp.web.sms.entity.ScanSubmit;
import com.neefull.fsp.web.sms.entity.TmsData;
import com.neefull.fsp.web.sms.service.IHeaderService;
import com.neefull.fsp.web.sms.service.IScanService;
import com.neefull.fsp.web.sms.service.ITmsDataService;
import com.neefull.fsp.web.sms.utils.ScanComment;
import com.neefull.fsp.web.sms.utils.SoapProperties;
import com.neefull.fsp.web.sms.utils.SoapWsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**同步数据给TMS
 * @Author: chengchengchu
 * @Date: 2020/12/8  14:36
 */

@Slf4j
@Component
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SubmitScan {

    private final static String code = "0000";


    @Autowired
    private IScanService scanService;
    @Autowired
    private IHeaderService headerService;
    @Autowired
    private ITmsDataService tmsDataService;



    public void submit(){
        //获取审核通过的DN信息
        List<Header> scanList = headerService.querySubmitDelivery();

        for (Header header : scanList) {
            //根据DN号查询所有的扫描信息
            List<Scan> list = scanService.queryScanByDelivery(header.getDelivery());

            List<Scan> scans = new ArrayList<>();
            for (Scan scan : list) {
                //判断这个物料是否未上传
                if(scan.getStatus().equals(ScanComment.STATUS_ONE)){
                    if(scans.size() < 100){
                        scans.add(scan);
                    }
//                    else {
//                        break;
//                    }
                }
            }

            //判断是否还有为推送的，没有更新状态为已推送
            if(scans.size()==0){
                //更新状态未上传成功
                headerService.updateStatus(header.getDelivery(),ScanComment.STATUS_FOUR);
            }else {

                List<ScanSubmit> scanSubmitList = new ArrayList<>();
                for (Scan sca : scans) {
                    ScanSubmit scanSubmit = new ScanSubmit();
                    scanSubmit.setKeyid("R"+String.valueOf(sca.getId()));
                    scanSubmit.setDnNo(sca.getDelivery());
                    scanSubmit.setBoxNo(sca.getBoxType()+"|"+sca.getBoxCode());
                    scanSubmit.setSerialNo("");
                    scanSubmit.setItemCode(sca.getMatCode());
                    scanSubmit.setItemName(sca.getMatName());
                    scanSubmit.setBatchNo("");
                    scanSubmit.setExipiryDate(sca.getExpiryDate());
                    scanSubmit.setUnit(sca.getUnit());
                    scanSubmit.setQuantity(sca.getQuantity());

                    scanSubmitList.add(scanSubmit);
                }

                String data = "{\"putSKUData\":{\"data\":{\"header\":"+JSON.toJSONString(scanSubmitList)+"}}}";

                log.info("对接TMS的DN号为：{}，对接data为：{}",header.getDelivery(),data);

                String sign = SoapWsUtils.getSign(data, SoapProperties.APPSECRET);

                String timestamp = Timestamp.valueOf(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")).toString();
                String res = "";
                try {
                    String time = URLEncoder.encode(timestamp, "UTF-8");
                    String encode = URLEncoder.encode(data, "UTF-8");

                    String url = SoapProperties.URL +"?" +
                            "method="+ SoapProperties.METHOD +"" +
                            "&client_customerid="+SoapProperties.CLIENTCUSTOMERID+"" +
                            "&client_db="+SoapProperties.CLIENTDB+"" +
                            "&messageid="+SoapProperties.MESSAGEID+"" +
                            "&apptoken="+SoapProperties.APPTOKEN+"" +
                            "&appkey="+SoapProperties.APPKEY+"" +
                            "&sign="+sign+"" +
                            "&timestamp="+time+"" +
                            "&format="+SoapProperties.FORMAT+"" +
                            "&appsecret="+SoapProperties.APPSECRET+"" +
                            "&data="+encode+"";
//                    log.info("对接TMS的请求url为: {}",url);

                    HttpPost post = new HttpPost(url);
                    HttpResponse response = HttpClients.createDefault().execute(post);
                    res = EntityUtils.toString(response.getEntity());

                    log.info("对接TMS的返回结果：{}",res);

                    if(res.contains(code)){
                        //更新扫描记录为已提交状态
                        for (Scan sca : scans) {
                            scanService.updateScanStatus(sca.getId(),ScanComment.STATUS_TWO);
                        }
                        //增加对接记录
                        TmsData tmsData = new TmsData();
                        tmsData.setDelivery(header.getDelivery());
                        tmsData.setData(data);
                        tmsData.setPlant(header.getPlant());
                        tmsData.setResData(res);
                        //添加
                        tmsDataService.addTmsData(tmsData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("对接TMS失败，失败原因为: {}",e.getMessage());

                    //失败 添加到系统操作日志里面
                    TmsData tmsData = new TmsData();
                    tmsData.setDelivery(header.getDelivery());
                    tmsData.setData(data);
                    tmsData.setPlant(header.getPlant());
                    tmsData.setResData(res);

                    tmsDataService.addTmsData(tmsData);
                }
            }
        }

    }

}
