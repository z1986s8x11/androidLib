package com.zsx.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

/**
 * 用户Android 6.0权限验证
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/5/5 10:25
 */
public class Lib_PermissionsHelper {
    private Activity activity;
    public final int REQUEST_CODE = 0x932;
    public String mMessage = "为了保证App正常运行,我们需要这些权限";
    public String mFirstMessage = "为了保证App正常运行,我们需要这些权限";

    public Lib_PermissionsHelper(Activity activity) {
        this.activity = activity;
    }

    /**
     * 请求权限
     *
     * @param permissions Manifest.permission.xxxxx
     * @return
     */
    public void _requestPermissions(String... permissions) {
        if (!_hasPermissions(permissions)) {
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE);
        }
    }

    public void _onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != REQUEST_CODE) {
            return;
        }
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                    //第一次
                    new AlertDialog.Builder(activity).setMessage(mFirstMessage).setNegativeButton("确定", null).create().show();
                } else {
                    //第N次  N>1
                    new AlertDialog.Builder(activity).setMessage(mMessage).setNegativeButton("取消", null).setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + activity.getPackageName()));
                            activity.startActivity(intent);
                        }
                    }).create().show();
                }
            }
        }
    }

    public boolean _hasPermissions(String... permissionsArray) {
        boolean result = true;
        for (String permission : permissionsArray) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                result = false;
                break;
            }
        }
        return result;
    }
}
