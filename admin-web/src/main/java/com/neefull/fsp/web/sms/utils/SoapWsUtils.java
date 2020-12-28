package com.neefull.fsp.web.sms.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * sap webservice的工具类，目的在于实现连接sapwebservice
 */
@Slf4j
public abstract class SoapWsUtils {


    private static final String encoding = "UTF-8";

    /**
     * 调用sap webservie
     *
     * @param invoke_ws_url 调用地址
     * @param soapMsg       soap消息
     * @return 返回的字符串
     * @throws Exception
     */
    public static String callWebService(String invoke_ws_url, String soapMsg) throws Exception {
        // 开启HTTP连接ַ
        InputStreamReader isr = null;
        BufferedReader inReader = null;
        StringBuffer result = null;
        OutputStream outObject = null;

        try {
            URL url = new URL(invoke_ws_url);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setRequestProperty("Content-Length", String.valueOf(soapMsg.getBytes().length));
            httpConn.setRequestProperty("Content-Type","text/xml; charset=utf-8");
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setConnectTimeout(500000);
            httpConn.setReadTimeout(500000);

            outObject = httpConn.getOutputStream();
            outObject.write(soapMsg.getBytes("utf-8"));

            log.info(httpConn.getResponseMessage());

            isr = new InputStreamReader(httpConn.getInputStream(), encoding);
            inReader = new BufferedReader(isr);
            result = new StringBuffer();
            String inputLine;
            while ((inputLine = inReader.readLine()) != null) {
                result.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inReader != null) {
                inReader.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (outObject != null) {
                outObject.close();
            }
        }
        return result.toString();
    }


    /**构建 soap请求报文   固定形式
     * @param delivery
     * @return
     */
    public static String getSoapMessage(String delivery){

        StringBuffer message = new StringBuffer("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:rfc:functions\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <urn:ZCHN_SD_GET_DN_INFO>\n" +
                "         <I_DELIVERY>"+delivery+"</I_DELIVERY>\n" +
                "         <T_DELIVERY_ITEM>\n" +
                "         \n" +
                "         </T_DELIVERY_ITEM>\n" +
                "         <T_MSG>\n" +
                "          \n" +
                "         </T_MSG>\n" +
                "      </urn:ZCHN_SD_GET_DN_INFO>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>");
        return message.toString();
    }

    public static String getSign(String data,String appSecret){

        String msg = appSecret+data+appSecret;

        String md5 = DigestUtils.md5DigestAsHex(msg.getBytes());

        String base64 = Base64.getEncoder().encodeToString(md5.getBytes()).toUpperCase();
        String encode = "";
        try {
            encode = URLEncoder.encode(base64, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("加密获取sign失败，失败原因为：{}" ,e.getMessage());
        }
        return encode;
    }

}
