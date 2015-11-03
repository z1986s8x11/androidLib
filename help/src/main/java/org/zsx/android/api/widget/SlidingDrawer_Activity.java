package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.widget.SlidingDrawer;

@SuppressWarnings("deprecation")
public class SlidingDrawer_Activity extends _BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_slidingdrawer);
		SlidingDrawer mSlidingDrawer = (SlidingDrawer) findViewById(R.id.act_widget_current_view);
		// 关闭时实现动画
		mSlidingDrawer.animateClose();
		// 即时关闭
		// mSlidingDrawer.close();
		// 指示SlidingDrawer是否在移动
		// mSlidingDrawer.isMoving();
		// 指示SlidingDrawer是否已全部打开
		// mSlidingDrawer.isOpened();
		// 屏蔽触摸事件
		// mSlidingDrawer.lock();
		// 关闭时调用
		// mSlidingDrawer.setOnDrawerCloseListener(null);
		// 解除屏蔽触摸事件
		// mSlidingDrawer.unlock();
		// 切换打开和关闭的抽屉SlidingDrawer
		// mSlidingDrawer.toggle();
	}
}
