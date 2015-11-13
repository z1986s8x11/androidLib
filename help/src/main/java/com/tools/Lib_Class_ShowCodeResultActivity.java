package com.tools;

import android.app.Activity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author zsx
 *
 * @date 2013-12-27 11:03:26
 *
 * @description 需要在AndroidMainifest.xml 注册
 * 				{@link com.tools.Lib_Class_ShowCodeResultActivity}
 */
public class Lib_Class_ShowCodeResultActivity extends Activity {
    public static final String RM_EXTRA_SHOW_CODE_FILE_KEY = "SHOW_CODE_FILE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout l = new LinearLayout(this);
        ScrollView mScrollView = new ScrollView(this);
        HorizontalScrollView hsv = new HorizontalScrollView(this);
        l.addView(mScrollView);
        mScrollView.addView(hsv);
        TextView t = new TextView(this);
        t.setEnabled(true);
        hsv.addView(t);
        setContentView(l);
        String fileName = getIntent().getStringExtra(RM_EXTRA_SHOW_CODE_FILE_KEY);
        if (fileName == null) {
            Toast.makeText(this, "文件路径为空", Toast.LENGTH_SHORT).show();
            return;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));
            StringBuffer sb = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                sb.append(str);
                sb.append("\n");
            }
            t.setText(sb.toString());
        } catch (IOException e) {
            Toast.makeText(this, "文件没有找到:" + String.valueOf(fileName), Toast.LENGTH_LONG).show();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}