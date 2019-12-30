package com.neefull.fsp.web.qff.entity;

import java.io.Serializable;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/30  13:28
 */
public class ImageQuery implements Serializable {

    private static final long serialVersionUID = -9152838069670023267L;

    private Integer dataId;
    private String relevance;
    private String deptName;
    private String url;

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
