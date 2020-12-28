package com.neefull.fsp.web.sms.entity;

import java.io.Serializable;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/8  15:58
 */
public class ScanSubmit implements Serializable {

    private static final long serialVersionUID = -1535909752079590854L;

    private String keyid;
    private String dnNo;
    private String boxNo;
    private String serialNo;
    private String itemCode;
    private String itemName;
    private String batchNo;
    private String exipiryDate;
    private String unit;
    private String quantity;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getDnNo() {
        return dnNo;
    }

    public void setDnNo(String dnNo) {
        this.dnNo = dnNo;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getExipiryDate() {
        return exipiryDate;
    }

    public void setExipiryDate(String exipiryDate) {
        this.exipiryDate = exipiryDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
