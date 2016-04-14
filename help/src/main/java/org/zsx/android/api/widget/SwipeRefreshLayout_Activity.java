package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ListView;

/**
 * 
 * @Author zsx
 * @date 2015-5-21
 */
public class SwipeRefreshLayout_Activity extends _BaseActivity implements OnRefreshListener {
	private SwipeRefreshLayout mRefreshLayout;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_swiperefreshlayout);
		mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.act_widget_current_view);
		mRefreshLayout.setColorSchemeColors(Color.YELLOW, Color.RED, Color.BLUE, Color.GREEN);
		mListView = (ListView) findViewById(R.id.listView);
		mRefreshLayout.setOnRefreshListener(this);
	}

	@Override
	public void onRefresh() {
		mListView.postDelayed(new Runnable() {

			@Override
			public void run() {
				mRefreshLayout.setRefreshing(false);
			}
		}, 3000);
	}
}
