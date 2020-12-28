package com.neefull.fsp.web.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/26  13:42
 */


@TableName(value = "sms_scan_log")
public class ScanLog implements Serializable {

    private static final long serialVersionUID = 8840227669651465845L;
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
     *  DN号调用sap返回的结果
     */
    @TableField(value = "delivery_response")
    private String deliveryResponse;
    /**
     *  工厂
     */
    @TableField(value = "plant")
    private String plant;

    @TableField(exist = false)
    private List<String> plants;

    /**
     *  状态   1未解析  2 已解析
     */
    @TableField(value = "status")
    private String status;
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

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

    public String getDeliveryResponse() {
        return deliveryResponse;
    }

    public void setDeliveryResponse(String deliveryResponse) {
        this.deliveryResponse = deliveryResponse;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
