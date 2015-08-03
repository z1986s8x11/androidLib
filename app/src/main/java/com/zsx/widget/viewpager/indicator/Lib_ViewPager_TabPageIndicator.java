/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zsx.widget.viewpager.indicator;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsx.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * This widget implements the dynamic action bar tab behavior that can change
 * across different configurations or circumstances.
 */
public class Lib_ViewPager_TabPageIndicator extends HorizontalScrollView
		implements PageIndicator {
	/** Title text used when no title is provided by the adapter. */
	private static final CharSequence EMPTY_TITLE = "";

	/**
	 * Interface for a callback when the selected tab has been reselected.
	 */
	public interface OnTabReselectedListener {
		/**
		 * Callback when the selected tab has been reselected.
		 * 
		 * @param position
		 *            Position of the current center item.
		 */
		void onTabReselected(int position);
	}

	private Runnable mTabSelector;

	private final OnClickListener mTabClickListener = new OnClickListener() {
		public void onClick(View view) {
			TabView tabView = (TabView) view;
			final int oldSelected = mViewPager.getCurrentItem();
			final int newSelected = tabView.getIndex();
			mViewPager.setCurrentItem(newSelected);
			if (oldSelected == newSelected && mTabReselectedListener != null) {
				mTabReselectedListener.onTabReselected(newSelected);
			}
		}
	};

	private final IcsLinearLayout mTabLayout;

	private ViewPager mViewPager;
	private OnPageChangeListener mListener;

	private int mMaxTabWidth;
	private int mSelectedTabIndex;

	private OnTabReselectedListener mTabReselectedListener;

	public Lib_ViewPager_TabPageIndicator(Context context) {
		this(context, null);
	}

	public Lib_ViewPager_TabPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		setHorizontalScrollBarEnabled(false);

		mTabLayout = new IcsLinearLayout(context);
		addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT,
				MATCH_PARENT));
	}

	public void setOnTabReselectedListener(OnTabReselectedListener listener) {
		mTabReselectedListener = listener;
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
		setFillViewport(lockedExpanded);

		final int childCount = mTabLayout.getChildCount();
		if (childCount > 1
				&& (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
			if (childCount > 2) {
				mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
			} else {
				mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
			}
		} else {
			mMaxTabWidth = -1;
		}

		final int oldWidth = getMeasuredWidth();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int newWidth = getMeasuredWidth();

		if (lockedExpanded && oldWidth != newWidth) {
			// Recenter the tab display if we're at a new (scrollable) size.
			_setCurrentItem(mSelectedTabIndex);
		}
	}

	private void animateToTab(final int position) {
		final View tabView = mTabLayout.getChildAt(position);
		if (mTabSelector != null) {
			removeCallbacks(mTabSelector);
		}
		mTabSelector = new Runnable() {
			public void run() {
				final int scrollPos = tabView.getLeft()
						- (getWidth() - tabView.getWidth()) / 2;
				smoothScrollTo(scrollPos, 0);
				mTabSelector = null;
			}
		};
		post(mTabSelector);
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mTabSelector != null) {
			// Re-post the selector we saved
			post(mTabSelector);
		}
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mTabSelector != null) {
			removeCallbacks(mTabSelector);
		}
	}

	private void addTab(int index, CharSequence text, int iconResId) {
		final TabView tabView = new TabView(getContext());
		tabView.mIndex = index;
		tabView.setFocusable(true);
		tabView.setOnClickListener(mTabClickListener);
		tabView.setText(text);

		if (iconResId != 0) {
			tabView.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0);
		}

		mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0,
				MATCH_PARENT, 1));
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		if (mListener != null) {
			mListener.onPageScrollStateChanged(arg0);
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if (mListener != null) {
			mListener.onPageScrolled(arg0, arg1, arg2);
		}
	}

	@Override
	public void onPageSelected(int arg0) {
		_setCurrentItem(arg0);
		if (mListener != null) {
			mListener.onPageSelected(arg0);
		}
	}

	@Override
	public void _setViewPager(ViewPager view) {
		if (mViewPager == view) {
			return;
		}
		if (mViewPager != null) {
			mViewPager.setOnPageChangeListener(null);
		}
		final PagerAdapter adapter = view.getAdapter();
		if (adapter == null) {
			throw new IllegalStateException(
					"ViewPager does not have adapter instance.");
		}
		mViewPager = view;
		view.setOnPageChangeListener(this);
		_notifyDataSetChanged();
	}

	@Override
	public void _notifyDataSetChanged() {
		mTabLayout.removeAllViews();
		PagerAdapter adapter = mViewPager.getAdapter();
		IconPagerAdapter iconAdapter = null;
		if (adapter instanceof IconPagerAdapter) {
			iconAdapter = (IconPagerAdapter) adapter;
		}
		final int count = adapter.getCount();
		for (int i = 0; i < count; i++) {
			CharSequence title = adapter.getPageTitle(i);
			if (title == null) {
				title = EMPTY_TITLE;
			}
			int iconResId = 0;
			if (iconAdapter != null) {
				iconResId = iconAdapter.getIconResId(i);
			}
			addTab(i, title, iconResId);
		}
		if (mSelectedTabIndex > count) {
			mSelectedTabIndex = count - 1;
		}
		_setCurrentItem(mSelectedTabIndex);
		requestLayout();
	}

	@Override
	public void _setViewPager(ViewPager view, int initialPosition) {
		_setViewPager(view);
		_setCurrentItem(initialPosition);
	}

	@Override
	public void _setCurrentItem(int item) {
		if (mViewPager == null) {
			throw new IllegalStateException("ViewPager has not been bound.");
		}
		mSelectedTabIndex = item;
		mViewPager.setCurrentItem(item);

		final int tabCount = mTabLayout.getChildCount();
		for (int i = 0; i < tabCount; i++) {
			final View child = mTabLayout.getChildAt(i);
			final boolean isSelected = (i == item);
			child.setSelected(isSelected);
			if (isSelected) {
				animateToTab(item);
			}
		}
	}

	private float fontSize = -1;
	private int selectedColor = Color.parseColor("#ff000000");
	private int defaultColor = Color.parseColor("#ffb2b2b2");
	private int backgroundResource = R.drawable.lib_selector_tab_indicator;

	public void _setTextSize(float size) {
		this.fontSize = size;
	}

	public void _setBackgroupResource(int resDrawable) {
		this.backgroundResource = resDrawable;
	}

	public void _setTextColor(int selectedColor, int defaultColor) {
		this.selectedColor = selectedColor;
		this.defaultColor = defaultColor;
	}

	public void _setTextColorRes(int selectedColorRes, int defaultColorRes) {
		this.selectedColor = getResources().getColor(selectedColorRes);
		this.defaultColor = getResources().getColor(defaultColorRes);
	}

	private int leftPadding;
	private int topPadding;
	private int rightPadding;
	private int bottomPadding;
	private boolean isSetPadding = false;

	public void _setTextPadding(int left, int top, int right, int bottom) {
		this.leftPadding = left;
		this.topPadding = top;
		this.rightPadding = right;
		this.bottomPadding = bottom;
		isSetPadding = true;
	}

	@Override
	public void _setOnPageChangeListener(OnPageChangeListener listener) {
		mListener = listener;
	}

	private class TabView extends TextView {
		private int mIndex;

		public TabView(Context context) {
			super(context);
			setGravity(Gravity.CENTER);
			if (fontSize != -1) {
				setTextSize(fontSize);
			}
			if (isSetPadding) {
				setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
			}
			setMaxLines(1);
			setTypeface(Typeface.DEFAULT_BOLD);
			// ColorStateList colors = new ColorStateList(
			// new int[][] { new int[] { android.R.attr.state_enabled },
			// new int[] { android.R.attr.state_window_focused },
			// new int[] { android.R.attr.state_pressed },
			// new int[] { android.R.attr.state_selected },
			// new int[] {} }, new int[] {
			// Color.parseColor("#ffb2b2b2"),
			// Color.parseColor("#ff000000"),
			// Color.parseColor("#ff000000"),
			// Color.parseColor("#ff000000"),
			// Color.parseColor("#ff000000") });
			// setTextColor(colors);
			ColorStateList colors = new ColorStateList(
					new int[][] { new int[] { android.R.attr.state_selected },
							new int[] {} }, new int[] { selectedColor,
							defaultColor });
			setTextColor(colors);
			setBackgroundResource(backgroundResource);
		}

		@Override
		public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);

			// Re-measure if we went beyond our maximum size.
			if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
				super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth,
						MeasureSpec.EXACTLY), heightMeasureSpec);
			}
		}

		public int getIndex() {
			return mIndex;
		}
	}

}
