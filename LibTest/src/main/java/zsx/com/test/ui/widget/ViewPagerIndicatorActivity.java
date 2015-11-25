package zsx.com.test.ui.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsx.adapter.Lib_BasePagerAdapter;
import com.zsx.widget.viewpager.indicator.Lib_ViewPager_CirclePageIndicator;
import com.zsx.widget.viewpager.indicator.Lib_ViewPager_LinePageIndicator;
import com.zsx.widget.viewpager.indicator.Lib_ViewPager_TabPageIndicator;
import com.zsx.widget.viewpager.indicator.Lib_ViewPager_TitlePageIndicator;
import com.zsx.widget.viewpager.indicator.Lib_ViewPager_UnderlinePageIndicator;

import java.util.Arrays;

import zsx.com.test.R;
import zsx.com.test.base._BaseFragmentActivity;

/**
 * Created by zhusx on 2015/8/7.
 */
public class ViewPagerIndicatorActivity extends _BaseFragmentActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private LinearLayout mLayout;
    private String[] names = new String[]{"第一个页面", "第二个页面", "第三个页面", "第四个页面", "第五个页面", "第六个页面"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_viewpagerindicator);
        findViewById(R.id.btn_tab).setOnClickListener(this);
        findViewById(R.id.btn_title).setOnClickListener(this);
        findViewById(R.id.btn_line).setOnClickListener(this);
        findViewById(R.id.btn_underLine).setOnClickListener(this);
        findViewById(R.id.btn_circle).setOnClickListener(this);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mLayout = (LinearLayout) findViewById(R.id.tab_layout);
        mViewPager.setAdapter(new Lib_BasePagerAdapter<String>(this, Arrays.asList(names)) {
            @Override
            public View getView(LayoutInflater inflater, int position, String s, View view, ViewGroup viewGroup) {
                TextView t = new TextView(inflater.getContext());
                t.setText(s);
                return t;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return names[position];
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tab:
                mLayout.removeAllViews();
                Lib_ViewPager_TabPageIndicator tabPager = new Lib_ViewPager_TabPageIndicator(this);
                tabPager._setTextPadding(20, 10, 20, 10);
                mLayout.addView(tabPager, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tabPager._setViewPager(mViewPager);
                break;
            case R.id.btn_title:
                mLayout.removeAllViews();
                Lib_ViewPager_TitlePageIndicator titlePager = new Lib_ViewPager_TitlePageIndicator(this);
                titlePager._setTextUnSelectColor(Color.GRAY);
                titlePager._setTextSelectedColor(Color.RED);
                mLayout.addView(titlePager, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                titlePager._setViewPager(mViewPager);
                break;
            case R.id.btn_line:
                mLayout.removeAllViews();
                Lib_ViewPager_LinePageIndicator linePager = new Lib_ViewPager_LinePageIndicator(this);
                mLayout.addView(linePager, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linePager._setViewPager(mViewPager);
                break;
            case R.id.btn_underLine:
                mLayout.removeAllViews();
                Lib_ViewPager_UnderlinePageIndicator underLinePager = new Lib_ViewPager_UnderlinePageIndicator(this);
                mLayout.addView(underLinePager, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));
                underLinePager._setViewPager(mViewPager);
                break;
            case R.id.btn_circle:
                mLayout.removeAllViews();
                Lib_ViewPager_CirclePageIndicator circlePager = new Lib_ViewPager_CirclePageIndicator(this);
                mLayout.addView(circlePager, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                circlePager._setViewPager(mViewPager);
                break;
        }
    }
}
