package zsx.com.test.ui.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class DragListView extends ListView implements AbsListView.OnScrollListener {

    boolean allowDragTop = true; // 如果是true，则允许拖动至底部的下一页
    float downY = 0;
    boolean needConsumeTouch = true; // 是否需要承包touch事件，needConsumeTouch一旦被定性，则不会更改

    private boolean flag = true;


    public DragListView(Context arg0) {
        this(arg0, null);
    }

    public DragListView(Context arg0, AttributeSet arg1) {
        this(arg0, arg1, 0);
    }

    public DragListView(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
        setOnScrollListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (flag) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                downY = ev.getRawY();
                needConsumeTouch = true; // 默认情况下，listView内部的滚动优先，默认情况下由该listView去消费touch事件
                allowDragTop = isAtTop();
            } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                if (!needConsumeTouch) {
                    // 在最顶端且向上拉了，则这个touch事件交给父类去处理
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                } else if (allowDragTop) {
                    // needConsumeTouch尚未被定性，此处给其定性
                    // 允许拖动到底部的下一页，而且又向上拖动了，就将touch事件交给父view
                    if (ev.getRawY() - downY > 2) {
                        // flag设置，由父类去消费
                        needConsumeTouch = false;
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                }
            }
            // 通知父view是否要处理touch事件
            getParent().requestDisallowInterceptTouchEvent(needConsumeTouch);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断listView是否在顶部
     *
     * @return 是否在顶部
     */
    private boolean isAtTop() {
        boolean resultValue = false;
        int childNum = getChildCount();
        if (childNum == 0) {
            // 没有child，肯定在顶部
            resultValue = true;
        } else {
            if (getFirstVisiblePosition() == 0) {
                // 根据第一个childView来判定是否在顶部
                View firstView = getChildAt(0);
                if (Math.abs(firstView.getTop() - getTop()) < 2) {
                    resultValue = true;
                }
            }
        }
        return resultValue;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;
        if (flag) {
            heightSpec = heightMeasureSpec;
        } else {
            heightSpec = MeasureSpec.makeMeasureSpec(536870911, -2147483648);
        }
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

    @Override
    public void onScrollStateChanged(AbsListView v, int i) {
        if (i == OnScrollListener.SCROLL_STATE_IDLE) {
            if (loadNextPageListener != null && v.getLastVisiblePosition() == v.getCount() - 1) {
                loadNextPageListener.loadNextPage();
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
    }


    public LoadNextPageListener loadNextPageListener;

    public void setLoadNextPageListener(LoadNextPageListener loadNextPageListener) {
        this.loadNextPageListener = loadNextPageListener;
    }

    public interface LoadNextPageListener {
        public void loadNextPage();
    }
}
