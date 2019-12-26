/*
package com.neefull.fsp.web.qff.aaa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

*
 * 包装QFF
 *
 * @Author: chengchengchu
 * @Date: 2019/12/6  17:27



@TableName(value = "qff_wrapper")
public class Wrapper implements Serializable {
    private static final long serialVersionUID = 7826019459474725542L;
*
     *  主键id


    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
*
     *  QFF编号


    private String number;
*
     *  Plant工厂


    private String plant;
*
     *  KDL Material 物料


    private Integer kMater;
*
     *  KDL SAP Batch 康德乐SAP批次


    private Integer kBatch;
*
     *  RD Material 罗氏物料号


    private Integer rMater;
*
     *  是否是危险品   0不危险  1危险


    private Integer isDanger;
*
     *  Principal Material 药厂物料号


    private Integer pMater;
*
     *  RD Batch 罗氏批号


    private Integer rBatch;
*
     *  Date of Manufacturing 生产日期


    private String manuDate;
*
     *  Expiry Date 有效期


    private String expiryDate;
*
     *  Quarantine 异常总数


    private Integer quarantine;
*
     *  Remark箱号/备注


    private String getRemark;
*
     *  QFF 发起日期


    private String initDate;
*
     *  RD QA confirmation 罗氏QA处理意见


    private String rConf;
*
     *  Time of repley 回复日期


    private String repTime;
*
     *  仪器工程师检查结果


    private String checkResult;
*
     *  QFF 退货原因


    private String reason;
*
     *  投诉编号


    private Integer compNumber;
*
     *  备注


    private String remark;
*
     *  BA


    private String ba;
*
     *  QFF 上报阶段


    private String stage;
*
     *  采购来源


    private String source;
*
     *  注册证号


    private String register;
*
     *  产品分类


    private String classify;
*
     *  状态码  1新建  2 审核中 3 完结  4删除


    private Integer status;
*
     *  创建日期


    private Date createTime;
*
     *  更新日期


    private Date updateTime;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getkMater() {
        return kMater;
    }

    public void setkMater(Integer kMater) {
        this.kMater = kMater;
    }

    public Integer getkBatch() {
        return kBatch;
    }

    public void setkBatch(Integer kBatch) {
        this.kBatch = kBatch;
    }

    public Integer getrMater() {
        return rMater;
    }

    public void setrMater(Integer rMater) {
        this.rMater = rMater;
    }

    public Integer getIsDanger() {
        return isDanger;
    }

    public void setIsDanger(Integer isDanger) {
        this.isDanger = isDanger;
    }

    public Integer getpMater() {
        return pMater;
    }

    public void setpMater(Integer pMater) {
        this.pMater = pMater;
    }

    public Integer getrBatch() {
        return rBatch;
    }

    public void setrBatch(Integer rBatch) {
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

    public Integer getQuarantine() {
        return quarantine;
    }

    public void setQuarantine(Integer quarantine) {
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

    public Integer getCompNumber() {
        return compNumber;
    }

    public void setCompNumber(Integer compNumber) {
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
*/
