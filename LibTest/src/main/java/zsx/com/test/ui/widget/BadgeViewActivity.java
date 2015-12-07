package zsx.com.test.ui.widget;

import android.os.Bundle;
import android.view.View;

import com.zsx.widget.Lib_Widget_BadgeView;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by Administrator on 2015/12/7.
 */
public class BadgeViewActivity extends _BaseActivity {
    Lib_Widget_BadgeView badge1;
    Lib_Widget_BadgeView badge2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_bageview);
        final View v1 = findViewById(R.id.btn_get);
        final View v2 = findViewById(R.id.btn_post);
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (badge1 == null) {
                    badge1 = new Lib_Widget_BadgeView(v.getContext(), v1);
                    badge1._setBadgeGravity(Lib_Widget_BadgeView.POSITION_TOP_RIGHT);
                    badge1.setText("2");
                }
                badge1._toggle();
                if (badge2 == null) {
                    badge2 = new Lib_Widget_BadgeView(v.getContext(), v2);
                    badge2._setBadgeGravity(Lib_Widget_BadgeView.POSITION_TOP_RIGHT);
                    badge2.setText("3");
                }
                badge2._toggle();
            }
        });
    }
}
