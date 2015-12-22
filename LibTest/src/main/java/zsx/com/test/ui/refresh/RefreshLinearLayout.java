package zsx.com.test.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/12/16.
 */
public class RefreshLinearLayout extends LinearLayout {
    TextView headView;

    public RefreshLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    ListView listView;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        headView = (TextView) getChildAt(0);
        listView = (ListView) getChildAt(1);
    }

    protected boolean isTop() {
        return true;
    }

    protected boolean isBottom() {
        return true;
    }

    @Override
    public final boolean onInterceptTouchEvent(MotionEvent event) {
        //如果没有滑动顶部 或者底部 不拦截
        if (!isTop() && !isBottom()) {
            return false;
        }
        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        //如果没有滑动顶部 或者底部 不拦截
        if (!isTop() && !isBottom()) {
            return false;
        }
        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(event);
    }
}
