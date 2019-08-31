package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_dict_banks")
public class DictBanks {
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField("sub_branch_name")
    private String subBranchName;
    @TableField("sub_branch_id")
    private String subBranchId;
    @TableField("province_id")
    private long provinceId;
    @TableField("province")
    private String province;
    @TableField("city_id")
    private long cityId;
    @TableField("city")
    private String city;
    @TableField("bank_name")
    private String bankName;
    @TableField("bank_id")
    private long bankId;
    @TableField("bank_abbr")
    private long bankAbbr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getSubBranchName() {
        return subBranchName;
    }

    public void setSubBranchName(String subBranchName) {
        this.subBranchName = subBranchName;
    }


    public String getSubBranchId() {
        return subBranchId;
    }

    public void setSubBranchId(String subBranchId) {
        this.subBranchId = subBranchId;
    }


    public long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public long getBankAbbr() {
        return bankAbbr;
    }

    public void setBankAbbr(long bankAbbr) {
        this.bankAbbr = bankAbbr;
    }
}
