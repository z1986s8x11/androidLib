package com.zsx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class Lib_BaseTwoAdapter<One, Two> extends Lib_BaseAdapter<One> {
	public Lib_BaseTwoAdapter(Context context, List<One> list) {
		super(context, list);
	}

	public Lib_BaseTwoAdapter(Context context) {
		super(context);
	}

	private List<Two> twoList = new ArrayList<Two>();
	private int twoCount = 0;
	private int limit = 5;

	public void _setLimit(int limit) {
		this.limit = limit;
		notifyDataSetChanged();
	}

	public void _setTwoList(List<Two> twoList) {
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
	public One getItem(int position) {
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
			return getTwoView(inflater,
					twoList.get(position / limit % twoList.size()), position
							/ limit, convertView, parent);
		} else {
			if (twoCount == 0) {
				return getOneView(inflater, p_list.get(position), position,
						convertView, parent);
			} else {
				return getOneView(inflater,
						p_list.get(position - position / limit), position
								- position / limit, convertView, parent);
			}
		}
	}

	public abstract View getTwoView(LayoutInflater inflater, Two bean,
			int position, View convertView, ViewGroup parent);

	public abstract View getOneView(LayoutInflater inflater, One bean,
			int position, View convertView, ViewGroup parent);

}
