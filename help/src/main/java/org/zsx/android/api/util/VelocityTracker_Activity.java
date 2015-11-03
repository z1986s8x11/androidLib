package org.zsx.android.api.util;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TextView;

/**
 * 用于手指在触摸屏上滑动的速率
 * 
 * @author zsx
 * 
 */
public class VelocityTracker_Activity extends _BaseActivity {
	private TextView mXTV;
	private TextView mYTV;
	private VelocityTracker mVelocityTracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.util_velocity_tracker);
		mXTV = (TextView) findViewById(R.id.global_textview1);
		mYTV = (TextView) findViewById(R.id.global_textview2);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mVelocityTracker == null) {
				/* 初始化 VelocityTracker */
				mVelocityTracker = VelocityTracker.obtain();
			} else {
				mVelocityTracker.clear();
			}
			mVelocityTracker.addMovement(event);
			break;
		case MotionEvent.ACTION_MOVE:
			mVelocityTracker.addMovement(event);
			/** 设置测量速度的时间单位包含多少毫秒 */
			mVelocityTracker.computeCurrentVelocity(1000);
			/** 手指在X方向的速度 至少addMovement 2次 */
			mXTV.setText("手指x 方向速度:" + mVelocityTracker.getXVelocity());
			/** 手指在Y方向的速度 至少addMovement 2次 */
			mYTV.setText("手指y 方向速度:" + mVelocityTracker.getYVelocity());
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if(mVelocityTracker!=null){
				//必须先clear() 不然出出现lllegalStateException
				mVelocityTracker.clear();
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			break;
		}
		return true;
	}
}
