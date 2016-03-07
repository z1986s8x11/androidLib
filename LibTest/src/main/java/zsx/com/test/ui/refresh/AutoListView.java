package zsx.com.test.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;

import zsx.com.test.ui.network.LoadData;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/7 14:54
 */
public class AutoListView extends Lib_SwipeListView<LoadData.Api, DataEntity, Object> {
    public AutoListView(Context context) {
        super(context);
    }

    public AutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AutoListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected IAutoView __initFootView() {
        IAutoView autoView = new FootView(getContext());
        return autoView;
    }
}
