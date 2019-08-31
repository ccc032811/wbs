package com.neefull.fsp.app.exception;

import lombok.Data;

import java.io.Serializable;

@Data
public class BizException extends Exception implements Serializable {

    private String  msg;
    public BizException(String msg)
    {
        super(msg);
    }

}
