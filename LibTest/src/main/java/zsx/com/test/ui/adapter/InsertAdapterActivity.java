package zsx.com.test.ui.adapter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zsx.adapter.Lib_BaseInsertAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by zhusx on 2015/8/5.
 */
public class InsertAdapterActivity extends _BaseActivity implements View.OnClickListener {
    Lib_BaseInsertAdapter<String, Integer> adapter;
    int i;
    int j = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_insert);
        ListView mListView = (ListView) findViewById(R.id.listView);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_clearData).setOnClickListener(this);
        findViewById(R.id.btn_clearInsert).setOnClickListener(this);

        mListView.setAdapter(adapter = new Lib_BaseInsertAdapter<String, Integer>(this) {

            @Override
            public View getView(LayoutInflater inflater, String bean, int position, View convertView, ViewGroup parent) {
                TextView t = new TextView(inflater.getContext());
                t.setPadding(10, 10, 10, 10);
                t.setText(bean);
                return t;
            }

            @Override
            public View getInsertView(LayoutInflater inflater, Integer bean, int position, View convertView, ViewGroup parent) {
                TextView t = new TextView(inflater.getContext());
                t.setPadding(10, 10, 10, 10);
                t.setText(String.valueOf(bean));
                Drawable d = getResources().getDrawable(android.R.drawable.ic_delete);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                t.setCompoundDrawables(d, null, null, null);
                return t;
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                adapter._addItemToUpdate(Arrays.asList(String.valueOf(i++), String.valueOf(i++), String.valueOf(i++), String.valueOf(i++)));
                break;
            case R.id.btn_insert:
                adapter._setInsertList(Arrays.asList(j++, j++, j++));
                break;
            case R.id.btn_clearData:
                adapter._clearToUpdate();
                break;
            case R.id.btn_clearInsert:
                adapter._setInsertList(new ArrayList<Integer>());
                break;
        }
    }
}
