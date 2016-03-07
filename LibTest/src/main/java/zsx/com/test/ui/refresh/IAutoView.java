package zsx.com.test.ui.refresh;

import android.view.View;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/7 17:18
 */
public interface IAutoView {
    void startLoad();

    void loadError(String error);

    void noMoreData();

    void reset();

    View getView();
}
