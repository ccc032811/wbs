package com.neefull.fsp.app.entity;

import java.io.Serializable;
import java.util.List;


public class ProjectSettlementWapper implements Serializable {

    private long projectId;

    private int isModel;

    private String totalAmount;

    private List<ProjectSettlement> projectSettlementList;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public int getIsModel() {
        return isModel;
    }

    public void setIsModel(int isModel) {
        this.isModel = isModel;
    }

    public List<ProjectSettlement> getProjectSettlementList() {
        return projectSettlementList;
    }

    public void setProjectSettlementList(List<ProjectSettlement> projectSettlementList) {
        this.projectSettlementList = projectSettlementList;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
