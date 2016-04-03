package com.zsx.network;

public class Lib_HttpResult<M> {
    private boolean isSuccess;
    private String message;
    private M data;
    private int errorCode = -1;
    private int currentDataIndex = CURRENT_INDEX_DEFAULT;
    public static final int CURRENT_INDEX_DEFAULT = -1;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getCurrentDataIndex() {
        return currentDataIndex;
    }

    void setCurrentDataIndex(int currentDataIndex) {
        this.currentDataIndex = currentDataIndex;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public M getData() {
        return data;
    }

    public void setData(M data) {
        this.data = data;
    }
}
