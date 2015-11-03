package org.zsx.android.api.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;
import org.zsx.android.api._BaseFragment;

public class Fragment_Activity extends _BaseActivity implements OnClickListener {
    private CustomFragment fragment1;
    private CustomFragment fragment2;
    private TextView showCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frgt_fragment);
        fragment1 = new CustomFragment("fragment1");
        fragment2 = new CustomFragment("fragment2");
        findViewById(R.id.global_btn1).setOnClickListener(this);
        findViewById(R.id.global_btn2).setOnClickListener(this);
        showCode = (TextView) findViewById(R.id.tv_showCode);
        showCode.setMovementMethod(ScrollingMovementMethod.getInstance());
        addLifecycle("activity", "onCreate");
    }

    public void addLifecycle(String className, String life) {
        showCode.append(className + ":" + life + "\n");
    }

    protected void onStart() {
        super.onStart();
        addLifecycle("activity", "onStart");
    }

    protected void onRestart() {
        super.onRestart();
        addLifecycle("activity", "onRestart");
    }

    protected void onResume() {
        super.onResume();
        addLifecycle("activity", "onResume");
    }

    protected void onPause() {
        super.onPause();
        addLifecycle("activity", "onPause");
    }

    protected void onStop() {
        super.onStop();
        addLifecycle("activity", "onStop");
    }

    protected void onDestroy() {
        super.onDestroy();
        addLifecycle("activity", "onDestroy");
    }

    _BaseFragment temp;

    @Override
    public void onClick(View v) {
        _BaseFragment a = null;
        _BaseFragment b = null;
        if (temp == null) {
            a = null;
            b = fragment1;
            temp = fragment1;
        } else if (temp == fragment1) {
            temp = fragment2;
            a = fragment1;
            b = fragment2;
        } else {
            temp = fragment1;
            a = fragment2;
            b = fragment1;
        }
        switch (v.getId()) {
            case R.id.global_btn1:
                _addFragment(R.id.act_widget_current_view, a, b);
                break;
            case R.id.global_btn2:
                _replaceFragment(R.id.act_widget_current_view, b);
                break;
        }
    }

    public static class CustomFragment extends _BaseFragment {
        private String key = "";

        public CustomFragment(String key) {
            this.key = key;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ((Fragment_Activity) getActivity()).addLifecycle(key,
                    "onActivityCreated");
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Fragment_Activity) getActivity()).addLifecycle(key, "onAttach");
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ((Fragment_Activity) getActivity()).addLifecycle(key, "onCreate");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ((Fragment_Activity) getActivity()).addLifecycle(key, "onCreateView");
            View v = inflater.inflate(android.R.layout.simple_list_item_1,
                    container, false);
            TextView t = (TextView) v.findViewById(android.R.id.text1);
            t.setText(key);
            return v;
        }

        @Override
        public void onStart() {
            super.onStart();
            ((Fragment_Activity) getActivity()).addLifecycle(key, "onStart");
        }

        @Override
        public void onResume() {
            super.onResume();
            ((Fragment_Activity) getActivity()).addLifecycle(key, "onResume");
        }

        @Override
        public void onPause() {
            super.onPause();
            ((Fragment_Activity) getActivity()).addLifecycle(key, "onPause");
        }

        @Override
        public void onStop() {
            super.onStop();
            ((Fragment_Activity) getActivity()).addLifecycle(key, "onStop");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            ((Fragment_Activity) getActivity()).addLifecycle(key, "onDestroy");
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ((Fragment_Activity) getActivity()).addLifecycle(key, "onDestroyView");
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ((Fragment_Activity) getActivity()).addLifecycle(key, "onViewCreated");
        }

        @Override
        public void onDetach() {
            super.onDetach();
            ((Fragment_Activity) getActivity()).addLifecycle(key, "onDetach");
        }

        @Override
        public void onHiddenChanged(boolean hidden) {
            super.onHiddenChanged(hidden);
            ((Fragment_Activity) getActivity()).addLifecycle(key,
                    "onHiddenChanged:" + String.valueOf(hidden));
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            ((Fragment_Activity) getActivity()).addLifecycle(key,
                    "onSaveInstanceState");
            super.onSaveInstanceState(outState);
        }
    }
}

