/*
 * Copyright (C) 2007-2013 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package com.zsx.debug;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zsx.R;

import java.util.List;

/**
 * 待测试
 */
public class Lib_BugReportActivity extends Activity implements View.OnClickListener {
    public static final String STACKTRACE = "mo kan";
    String msgText;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        setContentView(R.layout.lib_activity_bug);
        msgText = getIntent().getStringExtra(STACKTRACE);
        ((TextView) findViewById(R.id.tv_message)).setText(msgText);
        findViewById(R.id.btn_ok).setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain"); // 纯文本
        intent.putExtra(Intent.EXTRA_SUBJECT, "错误信息");
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(Intent.createChooser(intent, "发送给开发者"), 11);
    }

    @Override
    public void onBackPressed() {
        restartActivity();
    }

    private void restartActivity() {
        try {
            PackageInfo packageinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            if (packageinfo == null) {
                return;
            }
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageinfo.packageName);
            List<ResolveInfo> resolveinfoList = getPackageManager()
                    .queryIntentActivities(resolveIntent, 0);
            while (resolveinfoList.iterator().hasNext()) {
                ResolveInfo resolveinfo = resolveinfoList.iterator().next();
                if (resolveinfo != null) {
                    if (resolveinfo.filter != null) {
                        if ("android.intent.action.MAIN".equals(resolveinfo.filter.getAction(0)) && "android.intent.category.LAUNCHER".equals(resolveinfo.filter.getCategory(0))) {
                            String packageName = resolveinfo.activityInfo.packageName;
                            String className = resolveinfo.activityInfo.name;
                            ComponentName cn = new ComponentName(packageName, className);
                            startActivity(Intent.makeRestartActivityTask(cn));
                            return;
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                restartActivity();
            }
        }
    }
}