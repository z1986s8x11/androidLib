package com.tools;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import com.zsx.debug.Lib_SourceCodeHelper;

/**
 * @author zsx
 * @date 2013-12-27 上午11:48:23
 */
public class Lib_Class_ShowCodeUtil {
    private Class<?>[] clsArr;
    Lib_SourceCodeHelper helper;

    public void setShowJava(Class<?>... clsArr) {
        this.clsArr = clsArr;
    }

    public void _onOptionsItemSelected(Context context, MenuItem item) {
        helper._onOptionsItemSelected(context, item);
    }

    public void _onCreateOptionsMenu(Context context, Menu menu) {
        if (clsArr != null) {
            helper = new Lib_SourceCodeHelper(clsArr[0]);
            helper._onCreateOptionsMenu(context, menu);
        }
    }
}
