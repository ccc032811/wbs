package com.neefull.fsp.web;

import com.alibaba.fastjson.JSON;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.sms.entity.*;
import com.neefull.fsp.web.sms.entity.vo.HeaderVo;
import com.neefull.fsp.web.sms.service.IDetailService;
import com.neefull.fsp.web.sms.service.IHeaderService;
import com.neefull.fsp.web.sms.service.IScanLogService;
import com.neefull.fsp.web.sms.service.IScanService;
import com.neefull.fsp.web.sms.utils.ScanComment;
import com.neefull.fsp.web.sms.utils.SoapWsUtils;
import com.neefull.fsp.web.sms.utils.XmlUtils;
import com.neefull.fsp.web.system.service.IFactoryService;
import com.neefull.fsp.web.system.service.IUserService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;


/**
 * @Author: chengchengchu
 * @Date: 2019/12/12  14:05
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplication extends BaseController {

    @Autowired
    private IHeaderService headerService;
    @Autowired
    private IDetailService detailService;
    @Autowired
    private IScanService scanService;
    @Autowired
    private IFactoryService factoryService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IScanLogService scanLogService;


    @Test
    public void testSoap(){

    }

    @Test
    public void scan(){
        scanService.auditDn("8000000727");

    }



    @Test
    public void sendUrl(){

        List<Scan> list = scanService.queryScanByDelivery("8000000727");

        List<Scan> scans = new ArrayList<>();
        for (Scan scan : list) {
            if(scan.getStatus().equals(ScanComment.STATUS_ONE)){
                if(scans.size() < 100){
                    scans.add(scan);
                }
            }
        }

        List<ScanSubmit> scanSubmitList = new ArrayList<>();
        for (Scan sca : scans) {
            ScanSubmit scanSubmit = new ScanSubmit();
            scanSubmit.setKeyid(String.valueOf(sca.getId()));
            scanSubmit.setDnNo(sca.getDelivery());
            scanSubmit.setBoxNo(sca.getBoxType()+"|"+sca.getBoxCode());
            scanSubmit.setSerialNo(sca.getSerialNumber());
            scanSubmit.setItemCode(sca.getMatCode());
            scanSubmit.setItemName(sca.getMatName());
            scanSubmit.setBatchNo(sca.getBatch());
            scanSubmit.setExipiryDate(sca.getExpiryDate());
            scanSubmit.setUnit(sca.getUnit());
            scanSubmit.setQuantity(sca.getQuantity());

            scanSubmitList.add(scanSubmit);
        }

        String data = "{\"putSKUData\":{\"data\":{\"header\":"+JSON.toJSONString(scanSubmitList)+"}}}";

        String sign = SoapWsUtils.getSign(data, "123456");

        String timestamp = Timestamp.valueOf(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")).toString();

        try {
            String time = URLEncoder.encode(timestamp, "UTF-8");
            String encode = URLEncoder.encode(data, "UTF-8");

            String url = "http://222.66.94.137:19192/datahub/SYKY_ERP/FluxWmsJsonApi?" +
                    "method=putORDREData" +
                    "&client_customerid=SYKY_ERP" +
                    "&client_db=FLUXWMSJSONDB" +
                    "&messageid=ORDRE" +
                    "&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025" +
                    "&appkey=test" +
                    "&sign="+sign+"" +
                    "&timestamp="+time+"" +
                    "&format=json" +
                    "&appsecret=123456" +
                    "&data="+encode+"";

            HttpPost post = new HttpPost(url);
            HttpResponse response = HttpClients.createDefault().execute(post);
            String s = EntityUtils.toString(response.getEntity());
            System.out.println("s = " + s);
            if(s.contains("0000")){
                System.out.println(true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    @Test
    public void test(){

        HeaderVo headerVo = headerService.queryScanDn("D005","8000000727");
        System.out.println(headerVo);
    }



}
