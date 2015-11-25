package zsx.com.test.ui.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsx.adapter.Lib_BasePagerAdapter;
import com.zsx.widget.Lib_Widget_DirectionalViewPager;

import java.util.Arrays;

import zsx.com.test.base._BaseActivity;

/**
 * Created by Administrator on 2015/11/25.
 */
public class DirectionViewPagerActivity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        Lib_Widget_DirectionalViewPager viewPager = new Lib_Widget_DirectionalViewPager(this);
        layout.addView(viewPager);
        viewPager._setOrientation(Lib_Widget_DirectionalViewPager.VERTICAL);
        setContentView(layout);
        viewPager.setAdapter(new Lib_BasePagerAdapter<String>(this, Arrays.asList("1", "2", "3", "4", "5", "6", "7")) {
            @Override
            public View getView(LayoutInflater inflater, int position, String s) {
                TextView t = new TextView(inflater.getContext());
                t.setText(s);
                return t;
            }
        });
    }
}
