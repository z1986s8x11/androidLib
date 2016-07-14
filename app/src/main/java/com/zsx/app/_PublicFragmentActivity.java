package com.zsx.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zsx.R;

/**
 * 公共的Activity 依托于传入的Fragment创建UI
 * Created by zhusx on 2015/9/25.
 */
public final class _PublicFragmentActivity extends Lib_BaseFragmentActivity {
    public static final String _EXTRA_FRAGMENT = "fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_layout_linearlayout);
        try {
            Class<Fragment> fragmentClass = (Class<Fragment>) getIntent().getSerializableExtra(_EXTRA_FRAGMENT);
            Fragment fragment = fragmentClass.newInstance();
            fragment.setArguments(getIntent().getExtras());
            _replaceFragment(R.id.lib_content, fragment);
        } catch (Exception e) {
            e.printStackTrace();
            _showToast("Fragment 初始化失败");
            finish();
        }
    }
}
