package com.neefull.fsp.web.sms.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/26  14:53
 */

@Component
public class SoapProperties implements InitializingBean {


    @Value("${sms.soap.soapUrl}")
    private String soapUrl;
    @Value("${sms.tms.url}")
    private String url;
    @Value("${sms.tms.method}")
    private String method;
    @Value("${sms.tms.client_customerid}")
    private String clientCustomerid;
    @Value("${sms.tms.client_db}")
    private String clientDb;
    @Value("${sms.tms.messageid}")
    private String messageId;
    @Value("${sms.tms.apptoken}")
    private String appToken;
    @Value("${sms.tms.appkey}")
    private String appKey;
    @Value("${sms.tms.format}")
    private String format;
    @Value("${sms.tms.appsecret}")
    private String appSecret;
    @Value(("${sms.opin.box_type}"))
    private String boxType;


    public static String SOAPURL;
    public static String URL;
    public static String METHOD;
    public static String CLIENTCUSTOMERID;
    public static String CLIENTDB;
    public static String MESSAGEID;
    public static String APPTOKEN;
    public static String APPKEY;
    public static String FORMAT;
    public static String APPSECRET;
    public static String BOXTYPE;

    @Override
    public void afterPropertiesSet() throws Exception {
        SOAPURL = this.soapUrl;
        URL = this.url;
        METHOD = this.method;
        CLIENTCUSTOMERID = this.clientCustomerid;
        CLIENTDB = this.clientDb;
        MESSAGEID = this.messageId;
        APPTOKEN = this.appToken;
        APPKEY = this.appKey;
        FORMAT = this.format;
        APPSECRET = this.appSecret;
        BOXTYPE = this.boxType;
    }




}
