package com.neefull.fsp.web.sms.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/28  10:35
 */


public class DetailScanVo implements Serializable {
    //1  表示有扫描记录   2表示没有扫描记录
    private String status;

    private String plant;

    private boolean type;

    private List<DetailVo> detailVoList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public List<DetailVo> getDetailVoList() {
        return detailVoList;
    }

    public void setDetailVoList(List<DetailVo> detailVoList) {
        this.detailVoList = detailVoList;
    }
}
