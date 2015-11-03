package org.zsx.android.api.view;

import org.zsx.android.api.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class SecureGroupView extends ViewGroup {

	public SecureGroupView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void init(Activity mActivity) {
		this.mActivity = mActivity;
	}

	Activity mActivity;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		spoofLayout(findViewById(R.id.global_textview1),
				mActivity.findViewById(R.id.global_btn2));
		spoofLayout(findViewById(R.id.global_textview2),
				mActivity.findViewById(R.id.global_btn3));
		spoofLayout(findViewById(R.id.global_textview3),
				mActivity.findViewById(R.id.global_btn4));
	}

	private void spoofLayout(View spoof, View original) {
		final int[] globalPos = new int[2];
		getLocationOnScreen(globalPos);
		int x = globalPos[0];
		int y = globalPos[1];

		original.getLocationOnScreen(globalPos);
		x = globalPos[0] - x;
		y = globalPos[1] - y;
		spoof.layout(x, y, x + original.getWidth(), y + original.getHeight());
	}
}
