package com.zsx.debug;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.zsx.app.Lib_BaseFragmentActivity;
import com.zsx.app._PublicFragmentActivity;

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
}
