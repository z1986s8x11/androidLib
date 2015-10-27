package zsx.com.test.ui.parse;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsx.util.Lib_Util_String;

import java.io.IOException;

import zsx.com.test.base._BaseActivity;

/**
 * Created by Administrator on 2015/10/27.
 */
public class JavaParseActivity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        TextView t = new TextView(this);
        t.setMovementMethod(ScrollingMovementMethod.getInstance());
        try {
            t.setText(Lib_Util_String.toString(getAssets().open("test.java")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HorizontalScrollView scrollView = new HorizontalScrollView(this);
        scrollView.addView(t);
        layout.addView(scrollView);
        setContentView(layout);
    }
}
