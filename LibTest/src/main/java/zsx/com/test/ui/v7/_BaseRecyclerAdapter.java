package zsx.com.test.ui.v7;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zsx.com.test.R;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/2 10:43
 */
public abstract class _BaseRecyclerAdapter<T> extends RecyclerView.Adapter<_BaseRecyclerAdapter._ViewHolder> {
    private List<T> mList;

    public _BaseRecyclerAdapter(List<T> list) {
        this.mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public _ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lib_list_item_1, parent, false);
        _ViewHolder viewHolder = new _ViewHolder(rootView);
        viewHolder.t = (TextView) rootView.findViewById(android.R.id.text1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(_ViewHolder holder, int position) {
        holder.t.setText(String.valueOf(mList.get(position)));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class _ViewHolder extends RecyclerView.ViewHolder {
        public TextView t;
        public View[] vs;

        public _ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public abstract View[] getView();
}
