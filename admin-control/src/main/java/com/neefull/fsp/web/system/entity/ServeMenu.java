package com.neefull.fsp.web.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: chengchengchu
 * @Date: 2020/2/10  14:14
 */

@TableName("serve_menu")
public class ServeMenu implements Serializable {

    private static final long serialVersionUID = -8554884856529763120L;
    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 服务名称
     */
    @TableField("name")
    private String name;
    /**
     * 服务地址
     */
    @TableField("adress")
    private String adress;
    /**
     * 服务状态  0新建  1上线  2下线
     */
    @TableField("status")
    private Integer status;
    /**
     * 创建人
     */
    @TableField("create_person")
    private String createPerson;
    /**
     * 修改人
     */
    @TableField("revise_person")
    private String revisePerson;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     *  创建日期
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    /**
     * 权限
     */
    @TableField(exist = false)
    private String perms;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getRevisePerson() {
        return revisePerson;
    }

    public void setRevisePerson(String revisePerson) {
        this.revisePerson = revisePerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }
}
