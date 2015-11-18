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
import org.zsx.android.api._BaseActivity;

import java.util.Arrays;

/**
 * Created by Administrator on 2015/11/18.
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
                t.setText(bean);
                return t;
            }
        });
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type = (String) parent.getItemAtPosition(position);
            }
        });
        seekBar = (SeekBar) findViewById(R.id.global_seekbar);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(this);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float f = 1.0f * progress / 100;
        if (fromUser) {
            switch (type) {
                case "setRotation":
                    view.setRotation(f);
                    break;
                case "setRotationX":
                    view.setRotationX(f);
                    break;
                case "setRotationY":
                    view.setRotationY(f);
                    break;
                case "setTranslationX":
                    view.setTranslationX(f);
                    break;
                case "setTranslationY":
                    view.setTranslationY(f);
                    break;
                case "setTranslationZ":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.setTranslationZ(f);
                    }
                    break;
                case "setScaleX":
                    view.setScaleX(f);
                    break;
                case "setScaleY":
                    view.setScaleY(f);
                    break;
                case "setAlpha":
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
