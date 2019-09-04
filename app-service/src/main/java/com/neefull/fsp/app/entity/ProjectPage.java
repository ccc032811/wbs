package com.neefull.fsp.app.entity;

import com.neefull.fsp.common.entity.QueryRequest;

import java.io.Serializable;

/**
 * 项目分页的包装类
 */
public class ProjectPage implements Serializable {

    private Project project;
    private QueryRequest queryRequest;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public QueryRequest getQueryRequest() {
        return queryRequest;
    }

    public void setQueryRequest(QueryRequest queryRequest) {
        this.queryRequest = queryRequest;
    }
}
