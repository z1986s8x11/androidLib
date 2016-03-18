package com.tools;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zsx.app.Lib_BaseFragmentActivity;

import org.zsx.android.api.R;

/**
 * @author zsx
 * @date 2013-12-27 11:03:26
 * @description 需要在AndroidMainifest.xml 注册
 * {@link com.tools.Lib_Class_ShowCodeResultActivity}
 */
public class Lib_Class_ShowCodeResultActivity extends Lib_BaseFragmentActivity {
    public static final String RM_EXTRA_SHOW_CODE_FILE_KEY = _EXTRA_String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_layout_linearlayout);
        Fragment fragment = new Lib_SourceCodeFragment();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.lib_content, fragment).commitAllowingStateLoss();
    }
}