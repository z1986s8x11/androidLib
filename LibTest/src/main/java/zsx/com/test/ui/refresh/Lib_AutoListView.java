package zsx.com.test.ui.refresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/7 10:13
 */
public class Lib_AutoListView extends ListView {
    private SwipeRefreshLayout swipeRefreshLayout;

    public Lib_AutoListView(Context context) {
        super(context);
        init();
    }

    public Lib_AutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Lib_AutoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Lib_AutoListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setCacheColorHint(Color.TRANSPARENT);
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
    }

    public void _setOnRefreshListener(OnRefreshListener listener) {
        if (swipeRefreshLayout == null) {
            ViewParent parent = getParent();
            if (parent == null) {
                throw new NullPointerException(" 必须有父容器");
            }
            if (!(parent instanceof ViewGroup)) {
                throw new IllegalArgumentException("parent must is ViewGroup");
            }
            ViewGroup viewGroup = (ViewGroup) parent;
            int index = 0;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i) == this) {
                    index = i;
                    break;
                }
            }
            viewGroup.removeView(this);
            ViewGroup.LayoutParams lp = getLayoutParams();
            swipeRefreshLayout = new SwipeRefreshLayout(getContext());
            swipeRefreshLayout.addView(this, new SwipeRefreshLayout.LayoutParams(SwipeRefreshLayout.LayoutParams.MATCH_PARENT, SwipeRefreshLayout.LayoutParams.MATCH_PARENT));
            viewGroup.addView(swipeRefreshLayout, index, lp);
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    public void _setRefresh(boolean refreshing) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(refreshing);
        }
    }

    public interface OnRefreshListener extends SwipeRefreshLayout.OnRefreshListener {
    }
}
