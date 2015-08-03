package com.zsx.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zsx.R;

/**
 * 
 * @author pt-zsx
 * 
 */
public class XListViewHeader extends LinearLayout {
	private final ProgressBar loadingPB;
	private final TextView messageTV;
	private String strLoading = "正在刷新...";
	private String strLoadComplete = "刷新完成";
	private String strLoadError = "刷新失败";
	private String strPushRefresh = "下拉刷新";
	private String strReleaseRefresh = "松开刷新";
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	private ImageView arrowIV;

	public XListViewHeader(Context context) {
		super(context);
		View v = LayoutInflater.from(context).inflate(
				R.layout.lib_base_widget_listview_header, this, false);
		loadingPB = (ProgressBar) v.findViewById(R.id.lib_progressbar);
		messageTV = (TextView) v.findViewById(R.id.lib_tv_refresh);
		arrowIV = (ImageView) v.findViewById(R.id.lib_iv_listview_arrow);
		addView(v);
	}

	public void _setTextColor(int color) {
		messageTV.setTextColor(color);
	}

	/****************************************************/
	/**
	 * 下拉状态 松开刷新
	 */
	protected void onDownReleaseToRefresh() {
		arrowIV.startAnimation(__getStartRotateAnimation());
		loadingPB.setVisibility(View.INVISIBLE);
		messageTV.setText(strReleaseRefresh);
	}

	/**
	 * 下拉刷新
	 * 
	 * @param isBack
	 *            是否由RELEASE_To_REFRESH 转变而来
	 */
	protected void onDownPullToRefresh(boolean isBack) {
		loadingPB.setVisibility(View.INVISIBLE);
		messageTV.setText(strPushRefresh);
		arrowIV.setVisibility(View.VISIBLE);
		if (isBack) {
			arrowIV.startAnimation(__getReverseRotateAnimation());
		}
	}

	/**
	 * 下拉状态,正在刷新...
	 */
	protected void onDownToRefreshing() {
		loadingPB.setVisibility(View.VISIBLE);
		messageTV.setText(strLoading);
		arrowIV.clearAnimation();
		arrowIV.setVisibility(View.INVISIBLE);
	}

	/**
	 * 当前状态，刷新完成done
	 */
	protected void onDoneToRefresh() {
		loadingPB.setVisibility(View.INVISIBLE);
		messageTV.setText(strLoadComplete);
		arrowIV.setVisibility(View.INVISIBLE);
	}
	/**
	 * 当前状态，刷新完成done
	 */
	protected void onDoneToError() {
		loadingPB.setVisibility(View.INVISIBLE);
		messageTV.setText(strLoadError);
		arrowIV.setVisibility(View.INVISIBLE);
	}
	protected RotateAnimation __getReverseRotateAnimation() {
		if (reverseAnimation == null) {
			reverseAnimation = new RotateAnimation(-180, 0,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			reverseAnimation.setInterpolator(new LinearInterpolator());
			reverseAnimation.setDuration(200);
			reverseAnimation.setFillAfter(true);
		}
		return reverseAnimation;
	}

	protected RotateAnimation __getStartRotateAnimation() {
		if (animation == null) {
			animation = new RotateAnimation(0, -180,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			animation.setInterpolator(new LinearInterpolator());
			animation.setDuration(250);
			animation.setFillAfter(true);
		}
		return animation;
	}

}
