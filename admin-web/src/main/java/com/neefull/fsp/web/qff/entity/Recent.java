package com.neefull.fsp.web.qff.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 近效期QFF
 *
 * @Author: chengchengchu
 * @Date: 2019/11/26  20:25
 */

@TableName(value = "qff_recent")
public class Recent implements Serializable {
    private static final long serialVersionUID = -6883719899970874252L;
    /**
     *  主键id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     *  运输单号
     */
    @TableField("transport")
    private String transport;
    /**
     *  康德乐物料号
     */
    @TableField("k_mater")
    private String kMater;
    /**
     *  罗氏物料号
     */
    @TableField("r_mater")
    private String rMater;
    /**
     *  产品名称
     */
    @TableField("name")
    private String name;
    /**
     *  有效期
     */
    @TableField("use_life")
    private String useLife;
    /**
     *  批号
     */
    @TableField("batch")
    private String batch;
    /**
     *  SAP 批次
     */
    @TableField("sap_batch")
    private String sapBatch;
    /**
     *  工厂
     */
    @TableField("factory")
    private String factory;
    /**
     *  库位
     */
    @TableField("ware_house")
    private String wareHouse;
    /**
     *  数量
     */
    @TableField("number")
    private String number;
    /**
     *  罗氏QA处理意见
     */
    @TableField("r_conf")
    private String rConf;
    /**
     *  回复日期
     */
    @TableField("rep_date")
    private String repDate;
    /**
     *  图片
     */
    @TableField(exist = false)
    private String images;
    /**
     *  状态码  1新建  2 审核中 3 完结  4异常
     */
    @TableField("status")
    private Integer status;
    /**
     *  创建日期
     */
    @TableField("create_time")
    private Date createTime;
    /**
     *  更新日期
     */
    @TableField("update_time")
    private Date updateTime;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getkMater() {
        return kMater;
    }

    public void setkMater(String kMater) {
        this.kMater = kMater;
    }

    public String getrMater() {
        return rMater;
    }

    public void setrMater(String rMater) {
        this.rMater = rMater;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUseLife() {
        return useLife;
    }

    public void setUseLife(String useLife) {
        this.useLife = useLife;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSapBatch() {
        return sapBatch;
    }

    public void setSapBatch(String sapBatch) {
        this.sapBatch = sapBatch;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getrConf() {
        return rConf;
    }

    public void setrConf(String rConf) {
        this.rConf = rConf;
    }

    public String getRepDate() {
        return repDate;
    }

    public void setRepDate(String repDate) {
        this.repDate = repDate;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
}
