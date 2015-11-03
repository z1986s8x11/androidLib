package org.zsx.android.api.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;
import org.zsx.android.api._BaseDialogFragment;

public class DialogFragment_Activity extends _BaseActivity implements
        OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frgt_dialogfragment);
        findViewById(R.id.global_btn1).setOnClickListener(this);
        findViewById(R.id.global_btn2).setOnClickListener(this);
    }

    public static class CustomDialogFragment extends _BaseDialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String message = "默认消息";
            if (getArguments() != null) {
                message = getArguments().getString("params");
            }
            Dialog dialog = new AlertDialog.Builder(getActivity())
                    .setMessage(message).setNegativeButton("确定", null).create();
            return dialog;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.global_btn1:
                new CustomDialogFragment().show(getSupportFragmentManager(), "tag1");
                break;
            case R.id.global_btn2:
                CustomDialogFragment frgt = new CustomDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("params", "我是参数");
                frgt.setArguments(bundle);
                frgt.show(getSupportFragmentManager(), "tag2");
                break;
            default:
                break;
        }
    }
}
