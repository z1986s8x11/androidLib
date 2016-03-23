package zsx.com.test.ui.parse;

import android.os.Bundle;
import android.util.Log;

import com.zsx.app.Lib_BaseFragmentActivity;
import com.zsx.debug.LogUtil;
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
        Log.i("haha", "i");
        Log.d("haha", "d");
        Log.w("haha", "w");
        Log.e("haha", "e");
        Log.e("[Laog]", "e");
        Log.e("asdada[Laog]dsadad", "e");
        Log.e("asdada[Log]dsadad", "e");
        LogUtil.e(this, "logutil");
        getSupportFragmentManager().beginTransaction().replace(R.id.lib_content, new P_LogCatFragment()).commitAllowingStateLoss();
    }
}
