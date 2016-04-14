package zsx.com.test.ui.design;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zsx.util._Arrays;
import com.zsx.widget.v7._BaseRecyclerAdapter;

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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new _BaseRecyclerAdapter<String>(this, _Arrays.asList("1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3")) {
            @Override
            public void __bindViewHolder(_ViewHolder holder, int position, String s) {
                holder.setText(android.R.id.text1, s);
            }

            @Override
            public int __getLayoutResource(int viewType) {
                return android.R.layout.simple_list_item_1;
            }
        });
    }
}
