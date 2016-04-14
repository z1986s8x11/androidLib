package org.zsx.android.api.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Activity_Activity extends _BaseActivity implements OnClickListener {
    @InjectView(R.id.global_textview1)
    public TextView mTextView;
    @InjectView(R.id.global_spinner)
    public Spinner mSpinner;
    private int[] styles = new int[]{android.R.style.Theme_Dialog,
            Window.FEATURE_NO_TITLE, Window.FEATURE_LEFT_ICON};
    private String[] stylesName = new String[]{
            "android.R.style.Theme_Dialog", "Window.FEATURE_NO_TITLE",
            "Window.FEATURE_LEFT_ICON"};

    /**
     * 创建Activity时调用
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.util_activity);
        ButterKnife.inject(this);
        mTextView.append("onCreate\n");
        findViewById(R.id.global_btn1).setOnClickListener(this);
        mSpinner.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1, stylesName));
        // finishAffinity(); 结束此Activity相关的所有Activity
        // recreate(); 重启此Activity
    }

    /**
     * 启动Activity时被调用
     */
    @Override
    protected void onStart() {
        mTextView.append("onStart\n");
        super.onStart();
    }

    /**
     * 重新启动Activity时被回调
     */
    @Override
    protected void onRestart() {
        mTextView.append("onRestart\n");
        super.onRestart();
    }

    /**
     * 恢复Activity时被回调
     */
    @Override
    protected void onResume() {
        mTextView.append("onResume\n");
        super.onResume();
    }

    /**
     * 暂停Activity时被回调
     */
    @Override
    protected void onPause() {
        mTextView.append("onPause\n");
        super.onPause();
    }

    /**
     * 停止Activity时被回调
     */
    @Override
    protected void onStop() {
        mTextView.append("onStop\n");
        super.onStop();
    }

    /**
     * 销毁Activity时被回调
     */
    @Override
    protected void onDestroy() {
        mTextView.append("onDestroy\n");
        super.onDestroy();
    }

    /**
     * 调用startActivityForResult(intent,requestCode)
     * 当对应Activity调用setResult()返回结果时会被回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.global_btn1:
                Intent in = null;
                if (mSpinner.getFirstVisiblePosition() == 0) {
                    in = new Intent(this,
                            Activity_Activity_TestDialogActivity.class);
                    in.putExtra(
                            Activity_Activity_TestDialogActivity._EXTRA_FLAG_KEY,
                            styles[mSpinner.getFirstVisiblePosition()]);
                } else {
                    in = new Intent(this, Activity_Activity_TestActivity.class);
                    in.putExtra(Activity_Activity_TestActivity._EXTRA_FLAG_KEY,
                            styles[mSpinner.getFirstVisiblePosition()]);
                }
                startActivity(in);
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                break;
        }
    }

}
