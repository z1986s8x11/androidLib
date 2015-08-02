package com.zsx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter 基类
 *
 *
 * Created by zhusx on 2015/7/31.
 */
public abstract class Lib_BaseAdapter<T> extends BaseAdapter {
    protected List<T> p_list = new ArrayList<>();
    protected LayoutInflater inflater;

    public Lib_BaseAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public Lib_BaseAdapter(Context context, List<T> list) {
        this.inflater = LayoutInflater.from(context);
        this.p_list = list;
    }

    @Override
    public int getCount() {
        return p_list.size();
    }

    @Override
    public T getItem(int position) {
        return p_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(inflater, p_list.get(position), position, convertView,
                parent);
    }

    /**
     * 拿到上下文
     */
    public Context _getContext() {
        return inflater.getContext();
    }

    /**
     * 返回当前的List
     */
    public List<T> _getListsData() {
        return p_list;
    }

    /**
     * 更新列表
     */
    public synchronized void _setItemsToUpdate(List<T> replaceList) {
        if (replaceList == null) {
            p_list.clear();
            notifyDataSetChanged();
            return;
        }
        this.p_list = replaceList;
        notifyDataSetChanged();
    }

    /**
     * 清空
     */
    public synchronized void _clearToUpdate() {
        p_list.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加项
     */
    public synchronized void _addItemToUpdate(T bean) {
        if (bean != null) {
            p_list.add(bean);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加项
     */
    public synchronized void _addItemToUpdate(List<T> bean) {
        if (bean != null) {
            if (bean.size() > 0) {
                p_list.addAll(bean);
                notifyDataSetChanged();
            }
        }
    }


    /**
     * 固定ListView 高度
     *
     * @param pull
     */
    public int _setFixListViewHeight(ListView pull) {
        ListAdapter adapter = pull.getAdapter();
        int totalHeight = 0;
        for (int i = 0, len = adapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = adapter.getView(i, null, pull);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = pull.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        params.height = totalHeight
                + (pull.getDividerHeight() * (pull.getCount() - 1) + 100);
        int currentScrollY = pull.getScrollY();
        pull.setLayoutParams(params);
        return currentScrollY;
    }


    public boolean _removeItemToUpdate(T m) {
        boolean isSuccess = p_list.remove(m);
        if (isSuccess) {
            notifyDataSetChanged();
        }
        return isSuccess;
    }

    public T _removeItemToUpdate(int position) {
        T bean = p_list.remove(position);
        if (bean != null) {
            notifyDataSetChanged();
        }
        return bean;
    }

    public abstract View getView(LayoutInflater inflater, T bean,
                                 int position, View convertView, ViewGroup parent);

    public View[] _getViewHolder(LayoutInflater inflater, View convertView,
                                 ViewGroup parent, int layoutId, int... viewIds) {
        View[] views;
        if (convertView == null) {
            convertView = inflater.inflate(layoutId, parent, false);
            views = new View[viewIds.length + 1];
            views[0] = convertView;
            for (int i = 0; i < viewIds.length; i++) {
                views[i + 1] = convertView.findViewById(viewIds[i]);
            }
            convertView.setTag(views);
        } else {
            views = (View[]) convertView.getTag();
        }
        return views;
    }

    public TextView _toTextView(View v) {
        return (TextView) v;
    }

    public ImageView _toImageView(View v) {
        return (ImageView) v;
    }

    public CheckBox _toCheckBox(View v) {
        return (CheckBox) v;
    }
}