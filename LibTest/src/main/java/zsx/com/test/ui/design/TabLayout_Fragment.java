package zsx.com.test.ui.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zsx.com.test.R;
import zsx.com.test.base._BaseFragment;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/13 15:38
 */
public class TabLayout_Fragment extends _BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.widget_design_tablayout, container, false);
        
        return rootView;
    }
}
