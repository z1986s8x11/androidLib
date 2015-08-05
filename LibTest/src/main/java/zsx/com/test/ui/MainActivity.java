package zsx.com.test.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;
import zsx.com.test.base._BaseAdapter;
import zsx.com.test.ui.debug.ExceptionActivity;

/**
 * Created by zhusx on 2015/8/5.
 */
public class MainActivity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new _BaseAdapter<String>(this, Arrays.asList("Debug", "我草", "我日")) {
            @Override
            public View getView(LayoutInflater inflater, String bean, final int position, View convertView, ViewGroup parent) {
                View[] vs = _getViewHolder(inflater, convertView, parent, R.layout.lib_list_item_1, android.R.id.text1);
                _toTextView(vs[1]).setText(bean);
                vs[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                startActivity(new Intent(v.getContext(), ExceptionActivity.class));
                                break;
                        }
                    }
                });
                return vs[0];
            }
        });
    }
}
