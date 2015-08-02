package com.zsx.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 
 * @author zsx
 * 
 */
public class Lib_Widget_ViewPager extends ViewPager {
	private boolean isScrollable = true;

	public Lib_Widget_ViewPager(Context context) {
		super(context);
	}

	public Lib_Widget_ViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 是否可以滑动
	 */
	public void _setScrollable(boolean isScrollable) {
		this.isScrollable = isScrollable;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (isScrollable) {
			return super.onTouchEvent(arg0);
		}
		getParent().requestDisallowInterceptTouchEvent(true);
		super.onTouchEvent(arg0);
		return false;

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (isScrollable) {
			return super.onInterceptTouchEvent(arg0);
		}
		switch (arg0.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_UP:
			super.onInterceptTouchEvent(arg0);
			return false;
		default:
			break;
		}
		return false;

	}
}
