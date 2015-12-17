package zsx.com.test.ui.widget;

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

    private boolean isIntercept;
    float interceptY;

    @Override
    public final boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                isIntercept = false;
                interceptY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isIntercept) {
                    return isIntercept;
                }
                if (interceptY < event.getY()) {
                    isIntercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isIntercept = false;
                break;
        }
        return isIntercept;
    }
//
//    @Override
//    public final boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction() & event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                break;
//        }
//        return true;
//    }
}
