package zsx.com.test.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * 下拉刷新ListView
 *
 * @description onRefreshComplete() Activity中调用, 通知ListView 刷新完成<br/>
 */
public class AutoListView extends ListView {

    public AutoListView(Context context) {
        super(context);
    }

    public AutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    RefreshHelper helper;

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            helper = new RefreshHelper(this);
            helper.initHeadView();
            setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, new String[]{"a", "b", "a", "b", "a", "b", "a", "b", "a", "b", "a", "b"}));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        helper.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (helper.dispatchTouchEvent(ev)) {
            return super.dispatchTouchEvent(ev);
        }
        return true;
    }
}
