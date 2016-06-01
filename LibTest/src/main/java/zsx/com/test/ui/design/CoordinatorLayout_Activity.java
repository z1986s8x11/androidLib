package zsx.com.test.ui.design;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsx.adapter.Lib_BasePagerAdapter;
import com.zsx.util._Arrays;
import com.zsx.widget.v7.Lib_BaseRecyclerAdapter;

import java.util.Arrays;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/13 15:23
 */
public class CoordinatorLayout_Activity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_design_coordinatorlayout);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new Lib_BasePagerAdapter<String>(this, Arrays.asList("1", "2", "3")) {
            @Override
            public View getView(LayoutInflater inflater, int position, String s, View convertView, ViewGroup container) {
                RecyclerView recyclerView = new RecyclerView(inflater.getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(new Lib_BaseRecyclerAdapter<String>(inflater.getContext(), _Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3")) {
                    @Override
                    public void __bindViewHolder(_ViewHolder holder, int position, String s) {
                        holder.setText(android.R.id.text1, s);
                    }

                    @Override
                    public int __getLayoutResource(int viewType) {
                        return android.R.layout.simple_list_item_1;
                    }
                });
                return recyclerView;
            }
        });
        ((TabLayout) findViewById(R.id.tabs)).setupWithViewPager(mViewPager);

    }
}
