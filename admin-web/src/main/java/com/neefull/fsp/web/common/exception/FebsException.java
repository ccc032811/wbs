package com.neefull.fsp.web.common.exception;

/**
 * FEBS系统内部异常
 *
 * @author pei.wang
 */
public class FebsException extends Exception {

    private static final long serialVersionUID = -994962710559017255L;

    public FebsException(String message) {
        super(message);
    }
}
