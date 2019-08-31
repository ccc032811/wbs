package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_plat_account")
public class PlatAccount {

    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField("ac_name")
    private String acName;
    @TableField("ac_no")
    private String acNo;
    @TableField("bank_name")
    private String bankName;
    @TableField("sub_branch")
    private String subBranch;
    @TableField("is_default")
    private int isDfault;
    @TableField("amount")
    private double amount;
    @TableField("pay_order")
    private int payOrder;
    @TableField("remark")
    private String remark;
    @TableField("weight")
    private int weight;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSubBranch() {
        return subBranch;
    }

    public void setSubBranch(String subBranch) {
        this.subBranch = subBranch;
    }

    public int getIsDfault() {
        return isDfault;
    }

    public void setIsDfault(int isDfault) {
        this.isDfault = isDfault;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(int payOrder) {
        this.payOrder = payOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
