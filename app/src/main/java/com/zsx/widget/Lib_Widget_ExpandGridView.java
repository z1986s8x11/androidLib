package com.zsx.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author zsx
 * @date 2015-3-10
 * @descripton 展开的GridView
 */
public class Lib_Widget_ExpandGridView extends GridView {
	/** 是否展开 */
	private boolean isExpand = true;

	public Lib_Widget_ExpandGridView(Context context) {
		super(context);
	}

	public Lib_Widget_ExpandGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Lib_Widget_ExpandGridView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 由于ListView和GridView都是可滑动的控件. 所以需要自定义GridView,重写其onMeasure()方法.
	 * 在该方法中使GridView的高为wrap_content的大小,否则GridView中 的内容只能显示很小一部分
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (isExpand) {
			int expandSpec = MeasureSpec.makeMeasureSpec(
					Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

	}

	/**
	 * @param isExpand
	 *            是否展开
	 */
	public void _setExpand(boolean isExpand) {
		this.isExpand = isExpand;
		invalidate();
	}
}
