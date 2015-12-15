package com.zsx.tools;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import com.zsx.debug.LogUtil;
import com.zsx.itf.Lib_LifeCycle;
import com.zsx.itf.Lib_OnCancelListener;
import com.zsx.util.Lib_Util_System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监听短信
 * Created by zhusx on 2015/12/9.
 */
public class Lib_SMSReceiver {
    private Uri uri = Uri.parse("content://sms/inbox");
    public static final String EXTRA__ID = "_id";
    public static final String EXTRA_BODY = "body";//短信内容
    public static final String EXTRA_ADDRESS = "address";//手机号
    public static final String EXTRA_PERSON = "person";//联系人姓名列表
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_TYPE = "type";
    private SmsObserver observer;
    private Context context;

    public <T extends Context & Lib_LifeCycle> Lib_SMSReceiver(final T context, OnSMSMessageListener onSMSMessage) {
        observer = new SmsObserver(context, new Handler(), onSMSMessage);
        this.context = context;
        if (!Lib_Util_System.isPermission(context, Manifest.permission.READ_SMS)) {
            if (LogUtil.DEBUG) {
                LogUtil.e(this, " please AndroidManifest.xml add  'android.permission.READ_SMS '");
            }
            return;
        }
        context.getContentResolver().registerContentObserver(uri, true, observer);
        context._addOnCancelListener(new Lib_OnCancelListener() {
            @Override
            public void onCancel() {
                context.getContentResolver().unregisterContentObserver(observer);
            }
        });
    }

    public void unregisterContentObserver() {
        context.getContentResolver().unregisterContentObserver(observer);
    }

    class SmsObserver extends ContentObserver {
        private Context context;
        private OnSMSMessageListener listener;

        public SmsObserver(Context context, Handler handler, OnSMSMessageListener listener) {
            super(handler);
            this.context = context;
            this.listener = listener;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            ContentResolver cr = context.getContentResolver();
            String[] projection = new String[]{EXTRA__ID, EXTRA_BODY, EXTRA_DATE, EXTRA_PERSON, EXTRA_ADDRESS, EXTRA_TYPE};
            String where = " date >  " + (System.currentTimeMillis() - 5 * 60 * 1000);
            Cursor cur = cr.query(uri, projection, where, null, "date desc");
            if (null == cur) {
                return;
            }
            List<Map<String, String>> list = new ArrayList<>();
            if (cur.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                map.put(EXTRA__ID, cur.getString(cur.getColumnIndex(EXTRA__ID)));
                map.put(EXTRA_ADDRESS, cur.getString(cur.getColumnIndex(EXTRA_ADDRESS)));
                map.put(EXTRA_BODY, cur.getString(cur.getColumnIndex(EXTRA_BODY)));
                map.put(EXTRA_PERSON, cur.getString(cur.getColumnIndex(EXTRA_PERSON)));
                map.put(EXTRA_DATE, cur.getString(cur.getColumnIndex(EXTRA_DATE)));
                map.put(EXTRA_TYPE, cur.getString(cur.getColumnIndex(EXTRA_TYPE)));
                if (LogUtil.DEBUG) {
                    LogUtil.e(this, String.valueOf(map));
                }
                list.add(map);
            }
            cur.close();
            //短信有可能被某些软件拦截后 放入黑名单,无法正常查询
            if (list.size() != 0) {
                if (listener != null) {
                    listener.onMessage(list);
                }
            }
        }
    }

    public interface OnSMSMessageListener {
        void onMessage(List<Map<String, String>> list);
    }
}
