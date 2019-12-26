package com.neefull.fsp.web.qff.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/6  17:24
 */

@TableName(value = "qff_commodity")
public class Commodity implements Serializable {


    private static final long serialVersionUID = -3702719768627092566L;
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
     *  QFF编号
     */
    @TableField("number")
    private String number;
    /**
     *  Plant工厂
     */
    @TableField("plant")
    private String plant;
    /**
     *  KDL Material 物料
     */
    @TableField("k_mater")
    private String kMater;
    /**
     *  KDL SAP Batch 康德乐SAP批次
     */
    @TableField("k_batch")
    private String kBatch;
    /**
     *  RD Material 罗氏物料号
     */
    @TableField("r_mater")
    private String rMater;
    /**
     *  是否是危险品   0不危险  1危险
     */
    @TableField("is_danger")
    private String isDanger;
    /**
     *  Principal Material 药厂物料号
     */
    @TableField("p_mater")
    private String pMater;
    /**
     *  RD Batch 罗氏批号
     */
    @TableField("r_batch")
    private String rBatch;
    /**
     *  Date of Manufacturing 生产日期
     */
    @TableField("manu_date")
    private String manuDate;
    /**
     *  Expiry Date 有效期
     */
    @TableField("expiry_date")
    private String expiryDate;
    /**
     *  Quarantine 异常总数
     */
    @TableField("quarantine")
    private String quarantine;
    /**
     *  Remark箱号/备注
     */
    @TableField("get_remark")
    private String getRemark;
    /**
     *  QFF 发起日期
     */
    @TableField("init_date")
    private String initDate;
    /**
     *  RD QA confirmation 罗氏QA处理意见
     */
    @TableField("r_conf")
    private String rConf;
    /**
     *  Time of repley 回复日期
     */
    @TableField("rep_time")
    private String repTime;
    /**
     *  仪器工程师检查结果
     */
    @TableField("check_result")
    private String checkResult;
    /**
     *  QFF 退货原因
     */
    @TableField("reason")
    private String reason;
    /**
     *  投诉编号
     */
    @TableField("comp_number")
    private String compNumber;
    /**
     *  备注
     */
    @TableField("remark")
    private String remark;
    /**
     *  BA
     */
    @TableField("ba")
    private String ba;
    /**
     *  QFF 上报阶段
     */
    @TableField("stage")
    private String stage;
    /**
     *  采购来源
     */
    @TableField("source")
    private String source;
    /**
     *  注册证号
     */
    @TableField("register")
    private String register;
    /**
     *  产品分类
     */
    @TableField("classify")
    private String classify;
    /**
     *  图片
     */
    @TableField("images")
    private String images;

    @TableField(exist = false)
    private List<String> imageList;

    @TableField(exist = false)
    private List<String> taskHistory;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getkMater() {
        return kMater;
    }

    public void setkMater(String kMater) {
        this.kMater = kMater;
    }

    public String getkBatch() {
        return kBatch;
    }

    public void setkBatch(String kBatch) {
        this.kBatch = kBatch;
    }

    public String getrMater() {
        return rMater;
    }

    public void setrMater(String rMater) {
        this.rMater = rMater;
    }

    public String getIsDanger() {
        return isDanger;
    }

    public void setIsDanger(String isDanger) {
        this.isDanger = isDanger;
    }

    public String getpMater() {
        return pMater;
    }

    public void setpMater(String pMater) {
        this.pMater = pMater;
    }

    public String getrBatch() {
        return rBatch;
    }

    public void setrBatch(String rBatch) {
        this.rBatch = rBatch;
    }

    public String getManuDate() {
        return manuDate;
    }

    public void setManuDate(String manuDate) {
        this.manuDate = manuDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getQuarantine() {
        return quarantine;
    }

    public void setQuarantine(String quarantine) {
        this.quarantine = quarantine;
    }

    public String getGetRemark() {
        return getRemark;
    }

    public void setGetRemark(String getRemark) {
        this.getRemark = getRemark;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getrConf() {
        return rConf;
    }

    public void setrConf(String rConf) {
        this.rConf = rConf;
    }

    public String getRepTime() {
        return repTime;
    }

    public void setRepTime(String repTime) {
        this.repTime = repTime;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCompNumber() {
        return compNumber;
    }

    public void setCompNumber(String compNumber) {
        this.compNumber = compNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBa() {
        return ba;
    }

    public void setBa(String ba) {
        this.ba = ba;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
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

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<String> getTaskHistory() {
        return taskHistory;
    }

    public void setTaskHistory(List<String> taskHistory) {
        this.taskHistory = taskHistory;
    }
}
