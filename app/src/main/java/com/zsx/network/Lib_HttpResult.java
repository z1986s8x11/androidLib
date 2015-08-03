package com.zsx.network;

public class Lib_HttpResult<M> {
	private boolean isSuccess;
	private String errorMessage;
	private M data;
	private int errorCode = -1;
	private int totalCount = TOTAL_COUNT_DEFAULT;
	public static final int TOTAL_COUNT_DEFAULT = -1;
	public static final int TOTAL_COUNT_ERROR = -2;
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return errorMessage;
	}

	public void setMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public M getData() {
		return data;
	}

	public void setData(M data) {
		this.data = data;
	}
}
