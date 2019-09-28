package com.neefull.fsp.app.entity;

import java.io.Serializable;

public class MsgWapper implements Serializable {

    private int count;
    private long userId;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
