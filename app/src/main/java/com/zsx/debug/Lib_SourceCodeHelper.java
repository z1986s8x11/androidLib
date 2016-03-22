package com.zsx.debug;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zsx.R;
import com.zsx.app.Lib_BaseFragmentActivity;
import com.zsx.app._PublicFragmentActivity;
import com.zsx.widget.slidingmenu.SlidingMenu;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/22 10:31
 */
public class Lib_SourceCodeHelper {
    private Class<?> cls;

    public Lib_SourceCodeHelper(Class<?> cls) {
        this.cls = cls;
    }

    public void _onOptionsItemSelected(Context context, MenuItem item) {
        switch (item.getGroupId()) {
            case 1:
                if (item.getItemId() == 1986) {
                    Intent in = new Intent(context, _PublicFragmentActivity.class);
                    in.putExtra(_PublicFragmentActivity._EXTRA_FRAGMENT, P_SourceCodeFragment.class);
                    in.putExtra(Lib_BaseFragmentActivity._EXTRA_String, "java/" + cls.getName().replace(".", "/") + ".java");
                    context.startActivity(in);
                }
                break;
            default:
                break;
        }

    }

    public void _onCreateOptionsMenu(Context context, Menu menu) {
        if (cls != null) {
            menu.add(1, 1986, 0, cls.getSimpleName());
        }
    }

    SlidingMenu mSlidingMenu;

    public void initContextView(Lib_BaseFragmentActivity activity) {
        if (mSlidingMenu == null) {
            mSlidingMenu = new SlidingMenu(activity, SlidingMenu.SLIDING_CONTENT);
            final View right = LayoutInflater.from(activity).inflate(R.layout.lib_layout_linearlayout, null, false);
            mSlidingMenu.setMenu(right);
            mSlidingMenu.setBehindWidth(activity._getFullScreenWidth() - 200);
            final String fileName = "java/" + activity.getClass().getName().replace(".", "/") + ".java";
            P_SourceCodeFragment fragment = new P_SourceCodeFragment();
            Bundle b = new Bundle();
            b.putString(Lib_BaseFragmentActivity._EXTRA_String, fileName);
            fragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.lib_content, fragment).commitAllowingStateLoss();
        }
    }
}
