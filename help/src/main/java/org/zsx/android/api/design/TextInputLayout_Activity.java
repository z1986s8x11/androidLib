package org.zsx.android.api.design;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/13 15:40
 */
public class TextInputLayout_Activity extends _BaseActivity {
    @InjectView(R.id.textInputLayout)
    public TextInputLayout mTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_design_textinputlayout);
        ButterKnife.inject(this);
        mTextInputLayout.setHint("输入");
        EditText editText = mTextInputLayout.getEditText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 4) {
                    mTextInputLayout.setErrorEnabled(true);
                    mTextInputLayout.setError("姓名长度不能超过4个");
                } else {
                    mTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
