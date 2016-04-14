package org.zsx.android.api.util;

import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.view.Window;

public class RequestFeature_Activity extends _BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** 设置状态栏不会挡在界面上面 */
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
	}
}
