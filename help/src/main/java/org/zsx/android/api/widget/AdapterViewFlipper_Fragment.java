package org.zsx.android.api.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsx.adapter.Lib_BaseAdapter;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseFragment;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/11/30 14:31
 */

public class AdapterViewFlipper_Fragment extends _BaseFragment {
    @InjectView(R.id.global_btn1)
    TextView prevTv;
    @InjectView(R.id.global_btn2)
    TextView nextTv;
    @InjectView(R.id.global_btn3)
    TextView autoTv;
    @InjectView(R.id.flipper)
    AdapterViewFlipper flipper;

    Integer[] resId = new Integer[]{R.drawable.iv_big_1, R.drawable.iv_big_2};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.widget_adapterviewflipper, container, false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        flipper.setAdapter(new Lib_BaseAdapter<Integer>(getActivity(), Arrays.asList(resId)) {
            @Override
            public View getView(LayoutInflater inflater, Integer bean, int position, View convertView, ViewGroup parent) {
                ImageView imageView = new ImageView(inflater.getContext());
                imageView.setImageResource(bean);
                return imageView;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @OnClick({R.id.global_btn1, R.id.global_btn2, R.id.global_btn3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.global_btn1:
                //停止自动播放
                flipper.stopFlipping();
                flipper.showPrevious();
                break;
            case R.id.global_btn2:
                //停止自动播放
                flipper.stopFlipping();
                flipper.showNext();
                break;
            case R.id.global_btn3:
                flipper.startFlipping();
                break;
        }
    }
}
