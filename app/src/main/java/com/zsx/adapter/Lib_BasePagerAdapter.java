package com.zsx.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class Lib_BasePagerAdapter<M, V extends View> extends
		PagerAdapter {
	private List<M> list;
	private Map<Integer, V> map;
	private LayoutInflater inflater;

	@SuppressLint("UseSparseArrays")
	public Lib_BasePagerAdapter(Context context, List<M> list) {
		this.list = list;
		map = new HashMap<Integer, V>();
		inflater = LayoutInflater.from(context);
	}
	public M _getItem(int position){
		return list.get(position);
	}
	public Lib_BasePagerAdapter(Context context) {
		this(context, new ArrayList<M>());
	}

	public abstract V getView(LayoutInflater inflater, int position, M m,
			V convertView);

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		V view = map.get(position);
		if (view == null) {
			view = getView(inflater, position, list.get(position), null);
		} else {
			view = getView(inflater, position, list.get(position), view);
		}
		container.addView(view);
		V upView = map.put(position, view);
		if (upView != view) {
			if (upView != null) {
				container.removeView(upView);
			}
		}
		return view;
	}

	public View[] _getViewHolder(LayoutInflater inflater, View convertView,
			int layoutId, int... viewIds) {
		View[] views;
		if (convertView == null) {
			convertView = inflater.inflate(layoutId, null, false);
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

	public void _setItemForUpdate(List<M> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void _addItemForUpdate(M m) {
		list.add(m);
		notifyDataSetChanged();
	}

	public void _addItemForUpdate(List<M> m) {
		list.addAll(m);
		notifyDataSetChanged();
	}

	public void _clearItemsForUpdate() {
		list.clear();
		notifyDataSetChanged();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(map.get(position));
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	public TextView _toTextView(View v) {
		return (TextView) v;
	}

	public ImageView _toImageView(View v) {
		return (ImageView) v;
	}
}
