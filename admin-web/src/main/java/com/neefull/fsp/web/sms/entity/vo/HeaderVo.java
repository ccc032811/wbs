package com.neefull.fsp.web.sms.entity.vo;

import java.io.Serializable;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/16  14:51
 */
public class HeaderVo implements Serializable {

    private static final long serialVersionUID = -4463994249461673406L;

    private String delivery;
    private String status;
    private String errorMessage;

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
