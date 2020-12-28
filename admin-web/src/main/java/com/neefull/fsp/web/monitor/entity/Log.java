package com.neefull.fsp.web.monitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.neefull.fsp.web.common.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pei.wang
 */
@Data
@TableName("sms_log")
@Excel("系统日志表")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 操作内容
     */
    @TableField("OPERATION")
    private String operation;

    /**
     * 耗时
     */
    @TableField("TIME")
    private Long time;


    /**
     * 方法参数
     */
    @TableField("PARAMS")
    private String params;

    /**
     * 操作者IP
     */
    @TableField("RESPONSE")
    private String response;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;


    private transient String createTimeFrom;
    private transient String createTimeTo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeFrom() {
        return createTimeFrom;
    }

    public void setCreateTimeFrom(String createTimeFrom) {
        this.createTimeFrom = createTimeFrom;
    }

    public String getCreateTimeTo() {
        return createTimeTo;
    }

    public void setCreateTimeTo(String createTimeTo) {
        this.createTimeTo = createTimeTo;
    }
}
