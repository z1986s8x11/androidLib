package zsx.com.test.ui.widget;

import android.os.Bundle;
import android.view.View;

import com.zsx.widget.Lib_Widget_KeywordsFlow;

import java.util.Arrays;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by zhusx on 2015/8/7.
 */
public class KeywordsFlowActivity extends _BaseActivity implements View.OnClickListener {
    private Lib_Widget_KeywordsFlow mKeywordsFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_keywordsflow);
        findViewById(R.id.btn_in).setOnClickListener(this);
        findViewById(R.id.btn_out).setOnClickListener(this);
        mKeywordsFlow = (Lib_Widget_KeywordsFlow) findViewById(R.id.keywordsFlow);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_in:
                mKeywordsFlow._showKeywords(Arrays.asList("张三","李四","王麻子", "王保长", "西门大官人"), Lib_Widget_KeywordsFlow._ANIMATION_TYPE.ANIMATION_IN);
                break;
            case R.id.btn_out:
                mKeywordsFlow._showKeywords(Arrays.asList("妲己", "杨贵妃", "貂蝉", "穆桂英"), Lib_Widget_KeywordsFlow._ANIMATION_TYPE.ANIMATION_OUT);
                break;
        }
    }
}
