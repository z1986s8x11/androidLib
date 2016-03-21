package org.zsx.android.api.service;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

/**
 * 未实现
 *
 * @author zsx
 */
@SuppressLint("NewApi")
public class NfcManager_Activity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_nfcmanager);
        NfcManager nfcManager = (NfcManager) getSystemService(Service.NFC_SERVICE);
        NfcAdapter adapter = nfcManager.getDefaultAdapter();
        if (adapter == null) {
            _showToast("手机没有NFC模块");
            return;
        }
        if (!adapter.isEnabled()) {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_info).setTitle("提示").setMessage("NFC未启用").setCancelable(false)
                    .setNegativeButton("退出", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NfcManager_Activity.this.finish();
                        }
                    }).setPositiveButton("去启用", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent in = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivityForResult(in, 0x811);
                }
            }).create().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0x811:

                break;
        }
    }
}
