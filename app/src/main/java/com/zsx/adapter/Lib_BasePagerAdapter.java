package com.zsx.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhusx on 2015/11/24.
 */
public abstract class Lib_BasePagerAdapter<T> extends PagerAdapter {
    private List<T> mList;
    private LayoutInflater mInflater;

    public Lib_BasePagerAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public Lib_BasePagerAdapter(Context context, List<T> list) {
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    public abstract View getView(LayoutInflater inflater, int position, T t);

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(mInflater, position, mList.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
