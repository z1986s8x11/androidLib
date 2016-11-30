package org.zsx.android.api.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.StackView;
import android.widget.Toast;

import com.zsx.adapter.Lib_BaseAdapter;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseFragment;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/11/30 15:10
 */

public class StackView_Fragment extends _BaseFragment {
    @InjectView(R.id.act_widget_current_view)
    StackView mStackView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.widget_stackview, container, false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        mStackView.setAdapter(new Lib_BaseAdapter<Integer>(getActivity(), Arrays.asList(new Integer[]{R.drawable.iv_big_1, R.drawable.iv_big_2, R.drawable.iv_big_1, R.drawable.iv_big_2})) {
            @Override
            public View getView(LayoutInflater layoutInflater, Integer integer, int i, View view, ViewGroup viewGroup) {
                ImageView iv = new ImageView(layoutInflater.getContext());
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setBackgroundResource(integer);
                return iv;
            }
        });
        mStackView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
