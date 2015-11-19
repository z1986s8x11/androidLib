package com.zsx.exception;

/**
 * @author zsx
 * @date 2015-02-05
 */
public class Lib_Exception extends Exception {
    private static final long serialVersionUID = 2339162990765061626L;
    public static final int ERROR_CODE_CANCEL = 19860811;
    private String message;
    private int errorCode = -1;

    public int _getErrorCode() {
        return errorCode;
    }

    public Lib_Exception(String message) {
        this.message = message;
    }

    public Lib_Exception(int code, String message) {
        this.message = message;
        this.errorCode = code;
    }

    public String _getErrorMessage() {
        return message;
    }
}
