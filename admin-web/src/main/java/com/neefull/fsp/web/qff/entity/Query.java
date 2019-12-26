package com.neefull.fsp.web.qff.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/10  15:42
 */
public class Query implements Serializable {
    private static final long serialVersionUID = -7654167783654524770L;

    // 当前页面数据量
    private int pageSize = 10;
    // 当前页码
    private int pageNum = 1;
    //物料号
    private String mater;
    //批次号
    private String batch;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //状态
    private Integer status;
    //类型
    private String stage;
    //流程的类型
    private String type;


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getMater() {
        return mater;
    }

    public void setMater(String mater) {
        this.mater = mater;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
