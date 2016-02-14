package zsx.com.test.ui.adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsx.util._Arrays;

import java.util.Random;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;
import zsx.com.test.base._BaseAdapter;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/2/14.16:54
 */
public class HorizontalActivity extends _BaseActivity {
    HorizontalListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        listView = (HorizontalListView) findViewById(R.id.listView);
        listView.setAdapter(new _BaseAdapter<String>(this, _Arrays.asList("1", "2", "3", "4", "5", "1", "2", "3", "4", "5", "1", "2", "3", "4", "5", "1", "2", "3", "4", "5")) {
            @Override
            public View getView(LayoutInflater inflater, String bean, int position, View convertView, ViewGroup parent) {
                TextView t;
                if (convertView == null) {
                    t = new TextView(inflater.getContext());
                    t.setGravity(Gravity.CENTER);
                } else {
                    t = (TextView) convertView;
                }
                t.setBackgroundColor(Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
                t.setPadding(50, 50, 50, 50);
                t.setText(bean);
                return t;
            }
        });
    }
}
