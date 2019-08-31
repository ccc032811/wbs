package com.neefull.fsp.app.exception;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorException extends RuntimeException implements Serializable {

    private String msg;

    public ErrorException(String msg) {
        super(msg);
    }

}
