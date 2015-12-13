package zsx.com.test.ui.parse;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zsx.debug.LogUtil;
import com.zsx.tools.Lib_Subscribes;
import com.zsx.util.Lib_Util_System;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by Administrator on 2015/12/13.
 */
public class LogcatActivity extends _BaseActivity implements View.OnClickListener {
    TextView infoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logcat);
        findViewById(R.id.btn_get).setOnClickListener(this);
        infoTV = (TextView) findViewById(R.id.tv_message);
        infoTV.setClickable(true);
        infoTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        Log.i("[]", "i");
        Log.d("[]", "d");
        Log.w("[]", "w");
        Log.v("[]", "v");
        Log.e("[]", "v");
    }

    @Override
    public void onClick(View v) {
        Lib_Subscribes.subscribe(new Lib_Subscribes.Subscriber<String>() {
            @Override
            public String doInBackground() {
                return Lib_Util_System.getLogCatForLogUtil();
            }

            @Override
            public void onComplete(String s) {
                infoTV.setText(s);
                LogUtil.e(this, String.valueOf(android.os.Process.myPid()));
            }

            @Override
            public void onError(Throwable t) {
                infoTV.setText("cuowu ");
            }
        });

    }
}
