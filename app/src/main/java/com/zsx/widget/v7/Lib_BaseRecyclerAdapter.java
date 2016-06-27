package com.zsx.widget.v7;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsx.util._Arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/2 10:43
 */
public abstract class Lib_BaseRecyclerAdapter<T> extends RecyclerView.Adapter<Lib_BaseRecyclerAdapter._ViewHolder> {
    private List<T> mList;
    protected LayoutInflater mLayoutInflater;

    public Lib_BaseRecyclerAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public Lib_BaseRecyclerAdapter(Context context, List<T> list) {
        this.mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public List<T> getListData() {
        return mList;
    }

    @Override
    public _ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        _ViewHolder viewHolder = new _ViewHolder(__getLayoutView(parent, viewType));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(_ViewHolder holder, int position) {
        __bindViewHolder(holder, position, mList.get(position));
    }

    public abstract void __bindViewHolder(_ViewHolder holder, int position, T t);

    public abstract int __getLayoutResource(int viewType);

    protected View __getLayoutView(ViewGroup parent, int viewType) {
        return mLayoutInflater.inflate(__getLayoutResource(viewType), parent, false);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class _ViewHolder extends RecyclerView.ViewHolder {
        public SparseArray viewHolder = new SparseArray();
        public View rootView;

        public _ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
        }

        public void setText(int id, String text) {
            ((TextView) getView(id)).setText(text);
        }

        public <T extends View> T getView(int id) {
            View childView = (View) viewHolder.get(id);
            if (childView == null) {
                childView = rootView.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }

    public void _addItemToUpdate(T t) {
        mList.add(t);
        notifyItemInserted(mList.size() - 1);
    }

    public void _addItemToUpdate(List<T> list) {
        if (!_Arrays.isEmpty(list)) {
            mList.addAll(list);
            notifyItemRangeInserted(mList.size() - list.size(), list.size());
        }
    }

    public void _removeItemToUpdate(T t) {
        int position = mList.indexOf(t);
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void _removeItemToUpdate(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void _moveItemToUpdate(int from, int to) {
        Collections.swap(mList, from, to);
        notifyItemMoved(from, to);
    }
}
