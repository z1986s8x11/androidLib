package com.zsx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class Lib_BaseInsertAdapter<T, I> extends Lib_BaseAdapter<T> {
    public Lib_BaseInsertAdapter(Context context, List<T> list) {
        super(context, list);
    }

    public Lib_BaseInsertAdapter(Context context) {
        super(context);
    }

    private List<I> twoList = new ArrayList<I>();
    private int twoCount = 0;
    private int limit = 5;

    public void _setLimit(int limit) {
        this.limit = limit;
        notifyDataSetChanged();
    }

    public void _setInsertList(List<I> twoList) {
        this.twoList = twoList;
        twoCount = twoList.size();
        notifyDataSetChanged();
    }

    public int getItemViewType(int position) {
        if (twoCount == 0) {
            return 1;
        }
        if ((position + limit + 1) % limit == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        if (twoCount == 0) {
            return super.getCount();
        }
        return p_list.size() + p_list.size() / (limit - 1);
    }

    @Override
    public T getItem(int position) {
        int index = position - (position + 1) / limit;
        return p_list.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == 0) {
            return getInsertView(inflater,
                    twoList.get(position / limit % twoList.size()), position
                            / limit, convertView, parent);
        } else {
            if (twoCount == 0) {
                return getView(inflater, p_list.get(position), position,
                        convertView, parent);
            } else {
                return getView(inflater,
                        p_list.get(position - position / limit), position
                                - position / limit, convertView, parent);
            }
        }
    }

    public abstract View getInsertView(LayoutInflater inflater, I bean,
                                       int position, View convertView, ViewGroup parent);

    public abstract View getView(LayoutInflater inflater, T bean,
                                 int position, View convertView, ViewGroup parent);

}
