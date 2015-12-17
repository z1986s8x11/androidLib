package zsx.com.test.ui.viewpagelooper;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsx.debug.LogUtil;
import com.zsx.widget.Lib_Widget_LooperViewPager;

import zsx.com.test.base._BaseActivity;

/**
 * Created by Administrator on 2015/11/23.
 */
public class ViewPagerLooperActivity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lib_Widget_LooperViewPager viewPager = new Lib_Widget_LooperViewPager(this);
        viewPager.setBoundaryCaching(true);
        setContentView(viewPager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                TextView convertView = new TextView(container.getContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                convertView.setText(String.valueOf(position));
                convertView.setTextColor(Color.BLACK);
                convertView.setGravity(Gravity.CENTER);
                container.addView(convertView);
                LogUtil.e(this, "======" + position);
                return convertView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
    }
}
