package com.neefull.fsp.web.sms.entity.copy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.neefull.fsp.web.sms.entity.Detail;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/23  17:58
 */

@TableName(value = "sms_header_copy")
public class HeaderCopy implements Serializable {

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
     *  售达方代码
     */
    @TableField(value = "sold_party")
    private String soldParty;
    /**
     *  售达方名称
     */
    @TableField(value = "sold_party_name")
    private String soldPartyName;
    /**
     *  送达方代码
     */
    @TableField(value = "ship_party")
    private String shipParty;
    /**
     *  送达方名称
     */
    @TableField(value = "ship_party_name")
    private String shipPartyName;
    /**
     *  送货地址
     */
    @TableField(value = "address")
    private String address;
    /**
     *  销售订单号
     */
    @TableField(value = "sales_order")
    private String salesOrder;
    /**
     *  药厂DO号
     */
    @TableField(value = "roche_delivery")
    private String rocheDelivery;
    /**
     *  药厂送达方代码
     */
    @TableField(value = "roche_ship_party")
    private String rocheShipParty;
    /**
     *  药厂送达方名称
     */
    @TableField(value = "roche_ship_party_name")
    private String rocheShipPartyName;
    /**
     *  药厂订单号
     */
    @TableField(value = "roche_order")
    private String rocheOrder;
    /**
     *  药厂客户
     */
    @TableField(value = "roche_customer_order")
    private String rocheCustomerOrder;
    /**
     *  工厂
     */
    @TableField(value = "plant")
    private String plant;

    @TableField(exist = false)
    private List<String> plants;
    /**
     *  审核用户
     */
    @TableField(value = "verify_user")
    private String verifyUser;
    /**
     *  审核时间
     */
    @TableField(value = "verify_date")
    private String verifyDate;
    /**
     *  状态   0未扫描  1 已扫描   2 审核未通过     3 审核通过   4 已同步
     */
    @TableField(value = "status")
    private String status;

    @TableField(value = "down_user")
    private String downUser;

    @TableField(value = "down_time")
    private String downTime;

    @TableField(value = "del_user")
    private String delUser;

    @TableField(value = "del_time")
    private String delTime;

    @TableField(value = "guid")
    private String guid;

    @TableField(value = "verify_code")
    private String verifyCode;
    /**
     *  错误信息
     */
    @TableField(value = "error_message")
    private String errorMessage;
    /**
     *  创建日期
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     *  更新日期
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    @TableField(exist = false)
    private List<Detail> detailList;

    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;
    /**
     * 总箱数
     */
    @TableField(exist = false)
    private String boxNum;

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

    public String getSoldParty() {
        return soldParty;
    }

    public void setSoldParty(String soldParty) {
        this.soldParty = soldParty;
    }

    public String getSoldPartyName() {
        return soldPartyName;
    }

    public void setSoldPartyName(String soldPartyName) {
        this.soldPartyName = soldPartyName;
    }

    public String getShipParty() {
        return shipParty;
    }

    public void setShipParty(String shipParty) {
        this.shipParty = shipParty;
    }

    public String getShipPartyName() {
        return shipPartyName;
    }

    public void setShipPartyName(String shipPartyName) {
        this.shipPartyName = shipPartyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        this.salesOrder = salesOrder;
    }

    public String getRocheDelivery() {
        return rocheDelivery;
    }

    public void setRocheDelivery(String rocheDelivery) {
        this.rocheDelivery = rocheDelivery;
    }

    public String getRocheShipParty() {
        return rocheShipParty;
    }

    public void setRocheShipParty(String rocheShipParty) {
        this.rocheShipParty = rocheShipParty;
    }

    public String getRocheShipPartyName() {
        return rocheShipPartyName;
    }

    public void setRocheShipPartyName(String rocheShipPartyName) {
        this.rocheShipPartyName = rocheShipPartyName;
    }

    public String getRocheOrder() {
        return rocheOrder;
    }

    public void setRocheOrder(String rocheOrder) {
        this.rocheOrder = rocheOrder;
    }

    public String getRocheCustomerOrder() {
        return rocheCustomerOrder;
    }

    public void setRocheCustomerOrder(String rocheCustomerOrder) {
        this.rocheCustomerOrder = rocheCustomerOrder;
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

    public String getVerifyUser() {
        return verifyUser;
    }

    public void setVerifyUser(String verifyUser) {
        this.verifyUser = verifyUser;
    }

    public String getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDownUser() {
        return downUser;
    }

    public void setDownUser(String downUser) {
        this.downUser = downUser;
    }

    public String getDownTime() {
        return downTime;
    }

    public void setDownTime(String downTime) {
        this.downTime = downTime;
    }

    public String getDelUser() {
        return delUser;
    }

    public void setDelUser(String delUser) {
        this.delUser = delUser;
    }

    public String getDelTime() {
        return delTime;
    }

    public void setDelTime(String delTime) {
        this.delTime = delTime;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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

    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
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

    public String getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(String boxNum) {
        this.boxNum = boxNum;
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
