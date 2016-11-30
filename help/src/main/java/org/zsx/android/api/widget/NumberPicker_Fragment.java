package org.zsx.android.api.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseFragment;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/11/30 14:55
 */

public class NumberPicker_Fragment extends _BaseFragment {
    @InjectView(R.id.act_widget_current_view)
    NumberPicker mNumberPicker;
    private String[] mDateDisplayValues = new String[7];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.widget_numberpicker, container, false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(6);
        Calendar cal = Calendar.getInstance();
        mNumberPicker.setDisplayedValues(null);
        for (int i = 0; i < 7; ++i) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            mDateDisplayValues[i] = (String) DateFormat.format("MM.dd EEEE", cal);
        }
        mNumberPicker.setDisplayedValues(mDateDisplayValues);
        mNumberPicker.setValue(7 / 2);
        mNumberPicker.invalidate();
        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                Toast.makeText(picker.getContext(),String.format("oldVal:%s newVal:%s",oldVal,newVal),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
