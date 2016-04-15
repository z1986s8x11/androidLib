package org.zsx.android.api.design;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/13 15:23
 */
public class CollapsingToolbarLayout_Activity extends _BaseActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_design_collapsingtollbar);
        //CollapsingToolbarLayout.setTitle("");当CollapsingToolbarLayout全屏没有折叠时，title显示的是大字体，在折叠的过程中，title不断变小到一定大小的效果。你可以调用setTitle(CharSequence)方法设置title
        //CollapsingToolbarLayout.setStatusBarScrim();调用方法setStatusBarScrim(Drawable)。还没研究明白，不过这个只能在Android5.0以上系统有效果。
    }
}
