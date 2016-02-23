package zsx.com.test.ui.chat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zsx.tools.Lib_SoftKeyboardStateHelper;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;
import zsx.com.test.base._BaseAdapter;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/2/18.9:15
 */
public class ChatActivity extends _BaseActivity implements View.OnClickListener, Lib_SoftKeyboardStateHelper.SoftKeyboardStateListener {
    _BaseAdapter<Editable> adapter;
    EditText mMessageET;
    ImageView mImageView;
    Lib_SoftKeyboardStateHelper keyHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        (keyHelper = new Lib_SoftKeyboardStateHelper(this))._addSoftKeyboardStateListener(this);
        ListView mListView = (ListView) findViewById(R.id.listView);
        mMessageET = (EditText) findViewById(R.id.et_message);
        mImageView = (ImageView) findViewById(R.id.iv_image);
        mImageView.setOnClickListener(this);
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        mListView.setAdapter(adapter = new _BaseAdapter<Editable>(this) {
            @Override
            public View getView(LayoutInflater inflater, Editable bean, int position, View convertView, ViewGroup parent) {
                TextView t = new TextView(inflater.getContext());
                t.setText(bean);
                return t;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                Editable message = mMessageET.getText();
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                mMessageET.setText("");
                adapter._addItemToUpdate(message);
                break;
            case R.id.btn_add:
                if (keyHelper._isSoftKeyboardOpened()) {
                    keyHelper._hideInputMethod(v.getContext());
                }
                if (mImageView.getVisibility() == View.GONE) {
                    mImageView.setVisibility(View.VISIBLE);
                } else {
                    mImageView.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_image:
                SpannableString msp = new SpannableString("[1]");
                //设置图片
                Drawable drawable = getResources().getDrawable(R.drawable.lib_base_iv_listview_arrow);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                msp.setSpan(new ImageSpan(drawable), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mMessageET.getText().append(msp);
                break;
        }
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        _showToast("打开");
        if (mImageView.getVisibility() == View.VISIBLE) {
            mImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        _showToast("关闭");
    }
}
