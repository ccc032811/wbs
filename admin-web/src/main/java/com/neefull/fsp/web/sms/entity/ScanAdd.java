package com.neefull.fsp.web.sms.entity;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/8  13:50
 */


public class ScanAdd implements Serializable {

    private static final long serialVersionUID = 4512763605004058369L;

    private String delivery;

    private String scans;

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getScans() {
        return scans;
    }

    public void setScans(String scans) {
        this.scans = scans;
    }

    public List<Scan> getScanList(){
        return JSONArray.parseArray(this.scans, Scan.class);
    }

}
