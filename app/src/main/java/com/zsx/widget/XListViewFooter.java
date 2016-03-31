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

public class XListViewFooter extends LinearLayout {
    private final ProgressBar loadingPB;
    private TextView loadMoreTV;
    public String doneStr = "滑动加载";
    public String noDataStr = "";
    public String doneToLoadMore = "正在加载...";
    public String loadMoreToError = "加载失败,点击重新加载";

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
        loadMoreTV.setText(doneStr);
    }

    public void _onNoData() {
        loadingPB.setVisibility(View.INVISIBLE);
        loadMoreTV.setText(noDataStr);
    }

    public void _onDoneToLoadMore() {
        loadingPB.setVisibility(View.VISIBLE);
        loadMoreTV.setText(doneToLoadMore);
    }

    public void _onLoadMoreToError() {
        loadingPB.setVisibility(View.INVISIBLE);
        loadMoreTV.setText(loadMoreToError);
    }
}
