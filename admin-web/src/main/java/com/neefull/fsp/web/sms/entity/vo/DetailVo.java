package com.neefull.fsp.web.sms.entity.vo;

import java.io.Serializable;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/10  14:29
 */



public class DetailVo implements Serializable {

    private static final long serialVersionUID = 6587075228203251485L;

    private String serialNumber;   //条形码
    private String matCode;        //物料号
    private String matName;        //物料名
    private String batch;          //批次
    private String quantity;       //数量
    private String scanQuantity;   //已扫数量
    private String expiryDate;     //有效期
    private String unit;           //单位

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMatCode() {
        return matCode;
    }

    public void setMatCode(String matCode) {
        this.matCode = matCode;
    }

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getScanQuantity() {
        return scanQuantity;
    }

    public void setScanQuantity(String scanQuantity) {
        this.scanQuantity = scanQuantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
