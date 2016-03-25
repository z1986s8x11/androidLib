package zsx.com.test.ui.scroll;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.zsx.util._AnimUtil;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/22 13:45
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TestScrollView extends ScrollView {
    public TestScrollView(Context context) {
        super(context);
    }

    public View titleV;


    public void setTitle(View titleV) {
        this.titleV = titleV;
        _AnimUtil.initVisibilityAnim(Gravity.CENTER, titleV);
    }

    public TestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TestScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (_isScrollTop()) {
            if (titleV.getVisibility() == View.GONE) {
                titleV.setVisibility(View.VISIBLE);
            }
        } else {
            if (titleV.getVisibility() == View.VISIBLE) {
                titleV.setVisibility(View.GONE);
            }
        }
    }

    ViewGroup mGroupView;
    int viewHeight;
    int viewTop;

    /**
     * 滑动到顶部
     */
    public boolean _isScrollTop() {
        if (mGroupView == null) {
            mGroupView = (ViewGroup) getChildAt(0);
            viewTop = mGroupView.getChildAt(1).getTop();
            viewHeight = mGroupView.getChildAt(1).getMeasuredHeight();
        }
        int p = getScrollY() - viewTop;
        if (p > 0 && p <= viewHeight) {
            return true;
        }
        return false;
    }
}
