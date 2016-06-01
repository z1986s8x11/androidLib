package zsx.com.test.ui.v7;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsx.widget.v7.Lib_BaseRecyclerAdapter;
import com.zsx.widget.v7.Lib_BaseRecyclerViewItemDecoration;

import java.util.Arrays;

import zsx.com.test.R;
import zsx.com.test.base._BaseFragment;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/19 16:05
 */
public class HeadRecyclerView_Fragment extends _BaseFragment {
    public RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_headrecyclervie, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        mRecyclerView.addItemDecoration(new Lib_BaseRecyclerViewItemDecoration(inflater.getContext()));
        mRecyclerView.setAdapter(new Lib_BaseRecyclerAdapter<String>(inflater.getContext(), Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")) {
            @Override
            public void __bindViewHolder(_ViewHolder holder, int position, String s) {
                holder.setText(android.R.id.text1, s);
                ((TextView) holder.getView(android.R.id.text1)).setTextColor(Color.RED);
            }

            @Override
            public int __getLayoutResource(int viewType) {
                return R.layout.lib_list_item_1;
            }
        });
        return rootView;
    }
}
