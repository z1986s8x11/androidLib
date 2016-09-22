package org.zsx.android.api.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

public class Activity_Activity_TestActivity extends _BaseActivity {
    public static final String _EXTRA_FLAG_KEY = "Activity_Activity_TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flag = getIntent().getIntExtra(_EXTRA_FLAG_KEY, -1);
        switch (flag) {
            case Window.FEATURE_NO_TITLE:
                requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
                break;
            case Window.FEATURE_LEFT_ICON:
                requestWindowFeature(Window.FEATURE_LEFT_ICON);
                break;
        }
        setContentView(R.layout.util_activity_test);
        switch (flag) {
            case Window.FEATURE_NO_TITLE:
                getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, android.R.layout.simple_list_item_1);
                TextView t = (TextView) findViewById(android.R.id.text1);
                t.setText("自定义View");
                break;
            case Window.FEATURE_LEFT_ICON:
                getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, android.R.drawable.ic_dialog_alert);
                break;
        }

    }
}
