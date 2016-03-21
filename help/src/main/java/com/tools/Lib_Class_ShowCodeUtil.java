package com.tools;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.zsx.app.Lib_BaseFragmentActivity;
import com.zsx.app._PublicFragmentActivity;

/**
 * @author zsx
 * @date 2013-12-27 上午11:48:23
 * @description 需要显示的数据必须存放在Assets目录下面<br/>
 * <ul>
 * assets目录下存放项目相关文件
 * <li>src</li>
 * <li>xml</li>
 * <li>layout</li>
 * <li>drawable</li>
 * <li>anmi</li>
 * <ul>
 */
public class Lib_Class_ShowCodeUtil {
    private Class<?>[] clsArr;

    public void setShowJava(Class<?>... clsArr) {
        this.clsArr = clsArr;
    }

    public void _onOptionsItemSelected(Context context, MenuItem item) {
        switch (item.getGroupId()) {
            case 1:
                if (item.getItemId() == 1986) {
                    Class<?> cls = clsArr[0];
                    Intent in = new Intent(context, _PublicFragmentActivity.class);
                    in.putExtra(_PublicFragmentActivity._EXTRA_FRAGMENT, Lib_SourceCodeFragment.class);
                    in.putExtra(Lib_BaseFragmentActivity._EXTRA_String, "java/" + cls.getName().replace(".", "/") + ".java");
                    context.startActivity(in);
                }
                break;
            default:
                break;
        }

    }

    public void _onCreateOptionsMenu(Context context, Menu menu) {
        if (clsArr != null) {
            menu.add(1, 1986, 0, clsArr[0].getSimpleName());
        }
    }
}
