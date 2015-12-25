package zsx.com.test.ui.parse;

import android.os.Bundle;

import com.zsx.app.Lib_BaseFragmentActivity;
import com.zsx.debug.P_LogCatFragment;

import zsx.com.test.R;

/**
 * Created by Administrator on 2015/12/13.
 */
public class LogcatActivity extends Lib_BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logcat);
        getSupportFragmentManager().beginTransaction().replace(R.id.lib_content, new P_LogCatFragment()).commitAllowingStateLoss();
    }
}
