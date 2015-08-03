package com.zsx.network;

import android.text.TextUtils;

public abstract class Lib_HttpLoadData<M, P> extends
		Lib_BaseHttpRequestData<Lib_HttpResult<M>, P> {

	public Lib_HttpLoadData(int id) {
		super(id);
	}

	@Override
	protected boolean __isSucess(int id, Lib_HttpResult<M> bean) {
		return bean.isSuccess();
	}

	@Override
	protected String __getErrorMessage(int id, Lib_HttpResult<M> bean) {
		if (TextUtils.isEmpty(bean.getMessage())) {
			return "";
		}
		return bean.getMessage();
	}

	@Override
	protected void __onError(int id, RequestData<P> requestData,
			Lib_HttpResult<M> result, boolean isAPIError, String error_message) {
		super.__onError(id, requestData, result, isAPIError, error_message);
		if (isAPIError) {
			if (result != null) {
				if (result.getTotalCount() == Lib_HttpResult.TOTAL_COUNT_DEFAULT) {
					result.setTotalCount(Lib_HttpResult.TOTAL_COUNT_ERROR);
				}
			}
		}
	}
}
