package com.zsx.debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsx.R;
import com.zsx.app.Lib_BaseFragment;

/**
 * Created by Administrator on 2015/12/13.
 */
public class P_LogCatFragment extends Lib_BaseFragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout rootView = new LinearLayout(inflater.getContext());
        rootView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout layoutTop = new LinearLayout(inflater.getContext());
        Button clearBtn = new Button(inflater.getContext());
        clearBtn.setText("清除Log");
        layoutTop.addView(clearBtn);
        Button getBtn = new Button(inflater.getContext());
        getBtn.setText("获取Log");
        getBtn.setOnClickListener(this);
        layoutTop.addView(getBtn);
        rootView.addView(layoutTop, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView infoTV = new TextView(inflater.getContext());
        rootView.addView(infoTV);
        return rootView;
    }

    @Override
    public void onClick(View v) {
    }
}
