package org.zsx.android.api.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsx.adapter.Lib_BasePagerAdapter;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/13 15:38
 */
public class TabLayout_Fragment extends _BaseFragment {
    @InjectView(R.id.viewPager)
    public ViewPager mViewPager;
    @InjectView(R.id.act_widget_current_view)
    public TabLayout mTabLayout;
    List<String> list = Arrays.asList("item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.widget_design_tablayout, container, false);
        ButterKnife.inject(this, rootView);
        mViewPager.setAdapter(new Lib_BasePagerAdapter<String>(getActivity(), list) {

            @Override
            public View getView(LayoutInflater inflater, int position, String s, View convertView, ViewGroup container) {
                TextView t = new TextView(inflater.getContext());
                t.setText(s);
                return t;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }
        });
        /**
         * TabLayout.MODE_SCROLLABLE 可以滑动 item数量多的时候用
         * TabLayout.MODE_FIXED  平均宽度  item数量少的时候用
         */
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        mTabLayout.addTab(mTabLayout.newTab().setIcon(android.R.drawable.ic_menu_add));
//        mTabLayout.setTabsFromPagerAdapter(mViewPager.getAdapter());
        mTabLayout.setupWithViewPager(mViewPager);
        return rootView;
    }
}
