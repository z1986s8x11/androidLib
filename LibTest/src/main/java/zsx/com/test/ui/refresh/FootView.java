package zsx.com.test.ui.refresh;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/7 17:25
 */
public class FootView implements IAutoView {
    TextView footView;

    public FootView(Context context) {
        footView = new TextView(context);
        footView.setGravity(Gravity.CENTER);
        footView.setPadding(30, 30, 30, 30);
    }

    @Override
    public void startLoad() {
        footView.setVisibility(View.VISIBLE);
        footView.setText("正在拉取数据");
    }

    @Override
    public void loadError(String error) {
        footView.setText("拉取失败");
    }

    @Override
    public void noMoreData() {
        footView.setText("没有数据了.");
    }

    @Override
    public void reset() {
        footView.setVisibility(View.GONE);
    }

    @Override
    public View getView() {
        return footView;
    }
}
