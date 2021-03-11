package com.neefull.fsp.web.sms.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.neefull.fsp.web.common.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  13:06
 */
@TableName(value = "sms_scan")
public class Scan implements Serializable {

    private static final long serialVersionUID = 314412906180314266L;
    /**
     *  主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     *  DN号
     */
    @TableField(value = "delivery")
    private String delivery;
    /**
     *  箱名
     */
    @TableField(value = "box_name")
    private String boxName;
    /**
     *  箱型
     */
    @TableField(value = "box_type")
    private String boxType;
    /**
     *  箱号
     */
    @TableField(value = "box_code")
    private String boxCode;
    /**
     *  物料号
     */
    @TableField(value = "mat_code")
    private String matCode;
    /**
     *  物料名
     */
    @TableField(value = "mat_name")
    private String matName;
    /**
     *  批次
     */
    @TableField(value = "batch")
    private String batch;
    /**
     *  条形码
     */
    @TableField(value = "serial_number")
    private String serialNumber;
    /**
     *  SN 码
     */
    @TableField(value = "sn_code")
    private String snCode;
    /**
     *  数量
     */
    @TableField(value = "quantity")
    private String quantity;

    @TableField(exist = false)
    private String count;
    /**
     *  有效期
     */
    @TableField(value = "expiry_date")
    private String expiryDate;
    /**
     *  工厂
     */
    @TableField(value = "plant")
    private String plant;

    @TableField(exist = false)
    private List<String> plants;
    /**
     *  单位
     */
    @TableField(value = "unit")
    private String unit;
    /**
     *  状态   1待同步   2待同步
     */
    @TableField(value = "status")
    private String status;
    /**
     *  状态   1有效    2删除
     */
    @TableField(value = "del")
    private String del;

    //  1新增  2 修改  3 删除
    @TableField(exist = false)
    private String flag;
    /**
     *
     */
    @TableField(value = "scan_user")
    private String scanUser;
    /**
     *
     */
    @TableField(value = "scan_date")
    private String scanDate;
    /**
     *
     */
    @TableField(value = "submit_user")
    private String submitUser;
    /**
     *
     */
    @TableField(value = "submit_date")
    private String submitDate;
    /**
     *
     */
    @TableField(value = "export_user")
    private String exportUser;
    /**
     *
     */
    @TableField(value = "export_date")
    private String exportDate;
    /**
     *
     */
    @TableField(value = "del_user")
    private String delUser;
    /**
     *
     */
    @TableField(value = "del_date")
    private String delDate;

    @TableField(value = "dummy_code")
    private String dummyCode;
    @TableField(value = "bar_code")
    private String barCode;
    @TableField(value = "del_guid")
    private String delGuid;
    @TableField(value = "head_row_id")
    private String headRowId;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;

    /**
     * 当前页面数据量
     */
    @TableField(exist = false)
    private Integer pageSize;
    /**
     * 当前页码
     */
    @TableField(exist = false)
    private Integer pageNum;

    @TableField(exist = false)
    private String soldParty;

    @TableField(exist = false)
    private String shipParty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public List<String> getPlants() {
        return plants;
    }

    public void setPlants(List<String> plants) {
        this.plants = plants;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public String getScanUser() {
        return scanUser;
    }

    public void setScanUser(String scanUser) {
        this.scanUser = scanUser;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(String submitUser) {
        this.submitUser = submitUser;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getExportUser() {
        return exportUser;
    }

    public void setExportUser(String exportUser) {
        this.exportUser = exportUser;
    }

    public String getExportDate() {
        return exportDate;
    }

    public void setExportDate(String exportDate) {
        this.exportDate = exportDate;
    }

    public String getDelUser() {
        return delUser;
    }

    public void setDelUser(String delUser) {
        this.delUser = delUser;
    }

    public String getDelDate() {
        return delDate;
    }

    public void setDelDate(String delDate) {
        this.delDate = delDate;
    }

    public String getDummyCode() {
        return dummyCode;
    }

    public void setDummyCode(String dummyCode) {
        this.dummyCode = dummyCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDelGuid() {
        return delGuid;
    }

    public void setDelGuid(String delGuid) {
        this.delGuid = delGuid;
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public String getHeadRowId() {
        return headRowId;
    }

    public void setHeadRowId(String headRowId) {
        this.headRowId = headRowId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getSoldParty() {
        return soldParty;
    }

    public void setSoldParty(String soldParty) {
        this.soldParty = soldParty;
    }

    public String getShipParty() {
        return shipParty;
    }

    public void setShipParty(String shipParty) {
        this.shipParty = shipParty;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
