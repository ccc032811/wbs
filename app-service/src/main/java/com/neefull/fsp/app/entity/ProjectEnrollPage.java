package com.neefull.fsp.app.entity;

import com.neefull.fsp.common.entity.QueryRequest;

import java.io.Serializable;

/**
 * 项目报名信息分页的包装类
 */
public class ProjectEnrollPage implements Serializable {

    private QueryProjectEncroll queryProjectEncroll;
    private QueryRequest queryRequest;

    public QueryProjectEncroll getQueryProjectEncroll() {
        return queryProjectEncroll;
    }

    public void setQueryProjectEncroll(QueryProjectEncroll queryProjectEncroll) {
        this.queryProjectEncroll = queryProjectEncroll;
    }

    public QueryRequest getQueryRequest() {
        return queryRequest;
    }

    public void setQueryRequest(QueryRequest queryRequest) {
        this.queryRequest = queryRequest;
    }
}
