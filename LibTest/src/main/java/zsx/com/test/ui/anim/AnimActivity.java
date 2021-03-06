package zsx.com.test.ui.anim;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zsx.tools.Lib_AnimatorHelper;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by Administrator on 2015/12/11.
 */
public class AnimActivity extends _BaseActivity implements View.OnClickListener {
    Button startBtn;
    Button endBtn;
    Lib_AnimatorHelper.AnimView animView1;
    Lib_AnimatorHelper.AnimView animView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_anim);
        startBtn = (Button) findViewById(R.id.btn_start);
        endBtn = (Button) findViewById(R.id.btn_end);
        endBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        Lib_AnimatorHelper animatorUtil = new Lib_AnimatorHelper(this);
        animView1 = animatorUtil.getAnimView(new Lib_AnimatorHelper.IMakeAnimView() {
            @Override
            public View makeView(Context context) {
                TextView t = new TextView(context);
                t.setTextColor(Color.RED);
                t.setGravity(Gravity.CENTER);
                t.setText("+1");
                t.setVisibility(View.GONE);
                return t;
            }
        });
        animView2 = animatorUtil.getAnimView(new Lib_AnimatorHelper.IMakeAnimView() {
            @Override
            public View makeView(Context context) {
                Button t = new Button(context);
                t.setText(startBtn.getText());
                t.setVisibility(View.GONE);
                return t;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_end:
                animView2.startMove(startBtn, endBtn);
                break;
            case R.id.btn_start:
                animView1.startTop(startBtn, 100);
                break;
        }
    }
}
