package zsx.com.test.ui.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsx.adapter.Lib_BasePagerAdapter;
import com.zsx.debug.LogUtil;
import com.zsx.widget.Lib_Widget_DirectionalViewPager;

import java.util.Arrays;
import java.util.Random;

import zsx.com.test.base._BaseActivity;

/**
 * Created by Administrator on 2015/11/25.
 */
public class DirectionViewPagerActivity extends _BaseActivity {
    Lib_Widget_DirectionalViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        viewPager = new Lib_Widget_DirectionalViewPager(this);
        layout.addView(viewPager);
        viewPager._setOrientation(Lib_Widget_DirectionalViewPager.VERTICAL);
        setContentView(layout);
        viewPager.setAdapter(new Lib_BasePagerAdapter<String>(this, Arrays.asList("1", "2", "3", "4", "5", "6", "7")) {
            @Override
            public View getView(LayoutInflater inflater, int position, String s, View view, ViewGroup viewGroup) {
                TextView t;
                if (view == null) {
                    LogUtil.e(this, "null" + position);
                    t = new TextView(inflater.getContext());
                    t.setBackgroundColor(Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
                } else {
                    LogUtil.e(this, "not null" + position);
                    t = (TextView) view;
                }
                t.setText(s);
                return t;
            }
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable savedState = viewPager.onSaveInstanceState();
        outState.putParcelable("hello", savedState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parcelable savedState = savedInstanceState.getParcelable("hello");
        viewPager.onRestoreInstanceState(savedState);
    }
}
