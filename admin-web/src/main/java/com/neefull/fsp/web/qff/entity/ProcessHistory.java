package com.neefull.fsp.web.qff.entity;

import java.io.Serializable;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/30  18:09
 */
public class ProcessHistory implements Serializable {


    private static final long serialVersionUID = -2558938364790173417L;
    private String name;
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
