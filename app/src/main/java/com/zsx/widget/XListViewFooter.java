/**
 * @author zsx
 */
package com.zsx.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zsx.R;
import com.zsx.itf.Lib_AutoLoadMoreListener;

public class XListViewFooter extends LinearLayout implements Lib_AutoLoadMoreListener {
	private final ProgressBar loadingPB;
	private TextView loadMoreTV;

	public XListViewFooter(Context context) {
		super(context);
		View v = LayoutInflater.from(context).inflate(R.layout.lib_widget_listview_footer, this, false);
		loadingPB = (ProgressBar) v.findViewById(R.id.lib_progressbar);
		loadMoreTV = (TextView) v.findViewById(R.id.lib_tv_loadMore);
		addView(v);
	}

	/****************************************************/
	public void _onDone() {
		loadingPB.setVisibility(View.INVISIBLE);
		loadMoreTV.setText("滑动加载更多");
	}

	public void _onNoData() {
		loadingPB.setVisibility(View.INVISIBLE);
		loadMoreTV.setText("亲~没有更多数据了");
	}

	public void _onDoneToLoadMore() {
		loadingPB.setVisibility(View.VISIBLE);
		loadMoreTV.setText("正在加载...");
	}

	public void _onLoadMoreToError() {
		loadingPB.setVisibility(View.INVISIBLE);
		loadMoreTV.setText("加载失败,点击重新加载");
	}
}
