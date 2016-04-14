package org.zsx.android.api.animator;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.zsx.adapter.Lib_BaseAdapter;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import java.util.Arrays;

/**
 * Created by zhusx on 2015/11/18.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewAnimator_Activity extends _BaseActivity implements SeekBar.OnSeekBarChangeListener {
    private View view;
    private Spinner spinner;
    private SeekBar seekBar;
    private String type = "setRotation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_view);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            _showToast("必须大于Android 3.0");
            finish();
            return;
        }
        view = findViewById(R.id.act_widget_current_view);
        spinner = (Spinner) findViewById(R.id.global_spinner);
        spinner.setAdapter(new Lib_BaseAdapter<String>(this, Arrays.asList("setRotation", "setRotationX", "setRotationY", "setTranslationX", "setTranslationY", "setTranslationZ", "setScaleX", "setScaleY", "setAlpha")) {
            @Override
            public View getView(LayoutInflater inflater, final String bean, int position, View convertView, ViewGroup parent) {
                TextView t = new TextView(inflater.getContext());
                t.setPadding(10, 10, 10, 10);
                t.setText(bean);
                return t;
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        seekBar = (SeekBar) findViewById(R.id.global_seekbar);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(this);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float f;
        if (fromUser) {
            switch (type) {
                case "setRotation":
                    f = 1.0f * progress;
                    view.setRotation(f);
                    break;
                case "setRotationX":
                    f = 1.0f * progress;
                    view.setRotationX(f);
                    break;
                case "setRotationY":
                    f = 1.0f * progress;
                    view.setRotationY(f);
                    break;
                case "setTranslationX":
                    f = 1.0f * progress;
                    view.setTranslationX(f);
                    break;
                case "setTranslationY":
                    f = 1.0f * progress;
                    view.setTranslationY(f);
                    break;
                case "setTranslationZ":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        f = 1.0f * progress;
                        view.setTranslationZ(f);
                    }
                    break;
                case "setScaleX":
                    f = 1.0f * progress / 100;
                    view.setScaleX(f);
                    break;
                case "setScaleY":
                    f = 1.0f * progress / 100;
                    view.setScaleY(f);
                    break;
                case "setAlpha":
                    f = 1.0f * progress / 100;
                    view.setAlpha(f);
                    break;
            }

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
