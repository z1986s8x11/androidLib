package zsx.com.test.ui.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsx.widget.Lib_Widget_PinnedSectionListView;

import java.util.Arrays;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by Administrator on 2015/11/17.
 */
public class PinnedSectionActivity extends _BaseActivity {
    private Lib_Widget_PinnedSectionListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_pinnedsectionlistview);
        mListView = (Lib_Widget_PinnedSectionListView) findViewById(R.id.listView);
        mListView._setAdapter(new Lib_Widget_PinnedSectionListView.PinnedSectionListAdapter<String>(this, Arrays.asList("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36".split(","))) {

            @Override
            public boolean isItemViewTypePinned(int viewType) {
                if (viewType == 1) {
                    return true;
                }
                if (viewType == 2) {
                    return true;
                }
                return false;
            }

            @Override
            public View getView(LayoutInflater inflater, String bean, int position, View convertView, ViewGroup parent) {
                TextView t = new TextView(inflater.getContext());
                if (getItemViewType(position) == 2) {
                    t.setTextColor(Color.GREEN);
                    t.setPadding(30, 30, 30, 30);
                } else if (getItemViewType(position) == 1) {
                    t.setTextColor(Color.RED);
                    t.setPadding(40, 40, 40, 40);
                } else {
                    t.setTextColor(Color.BLACK);
                    t.setPadding(20, 20, 20, 20);
                }

                t.setText(bean);
                return t;
            }

            @Override
            public int getItemViewType(int position) {
                if (position == 4) {
                    return 1;
                }
                if (position == 8) {
                    return 2;
                }
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 3;
            }
        });
    }
}
