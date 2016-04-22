package zsx.com.test.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/21 13:49
 */
public class ScrollViewGroup extends Lib_Widget_BasePullLoadDataLayout<Integer, String, String> {
    private final float DRAG_RATE = 0.5f;

    public ScrollViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void __bindData(boolean isRefresh, String data) {

    }

    View mTarget;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View headView = getChildAt(0);
        mTarget = getChildAt(1);
        _setRefreshView(headView);
//        LoadData loadData;
//        init(loadData = new LoadData(1));
//        loadData._refreshData();
    }

    @Override
    protected void __scroll(View headView, int height, float overscrollTop) {
        float offset = overscrollTop - height;
        if (offset < 0) {
            headView.setPadding(0, (int) offset, 0, 0);
        } else {
            headView.setPadding(0, (int) (offset * DRAG_RATE), 0, 0);
        }
    }

    @Override
    protected void __start(View headView, int height) {
        headView.setPadding(0, 0, 0, 0);
    }

    @Override
    protected void __reset(View headView, int height, int type, String error) {
        headView.setPadding(0, -height, 0, 0);
    }

    @Override
    protected boolean isToRefresh(View headView, int height, float overscrollTop) {
        return overscrollTop > height;
    }
}
