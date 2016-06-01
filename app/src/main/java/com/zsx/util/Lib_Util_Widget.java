package com.zsx.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author zsx
 * @date 2013-12-27 上午11:22:32
 * @description
 */
public class Lib_Util_Widget {
    /**
     * 获取字符串在该View上面显示的宽度
     *
     * @param v
     * @param text
     * @return
     */
    public static float getFontWidth(TextView v, String text) {
        return v.getPaint().measureText(text);
    }

    /**
     * 获得绘制文本的宽和高
     */
    public static Rect getTextRect(TextView v, String text) {
        Rect mBound = new Rect();
        v.getPaint().getTextBounds(text, 0, text.length(), mBound);
        return mBound;
    }

    /**
     * getAdapter()!=null<br/>
     * 通过设置的Adapter 重新设置ListView 高度
     */
    public static void setListViewHeight(ListView listView) {
        setListViewHeight(listView, Integer.MAX_VALUE);
    }

    /**
     * getAdapter()!=null<br/>
     * 通过设置的Adapter 重新设置ListView 高度
     */
    public static void setListViewHeight(ListView listView, int count) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null || count < 0) {
            return;
        }

        int totalHeight = 0;
        count = Math.min(count, listAdapter.getCount());
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }

            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewParent parent = listView.getParent();
        if (parent instanceof AdapterView) {
            int w = LayoutParams.MATCH_PARENT;
            int h = totalHeight
                    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            LayoutParams params = new LayoutParams(w, h);
            listView.setLayoutParams(params);
        } else if (parent instanceof ViewGroup) {
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight
                    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

    /**
     * 屏幕宽度,单位像素(px).
     */
    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 拿到屏幕的高度
     */
    public static int getHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 计算child 的 width 以及 height 通过 child.getMeasuredHeight()获取高;
     */
    public static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 插一个父View
     *
     * @return 父View
     */
    public static LinearLayout insertParentLayout(View resLayout) {
        ViewParent parent = resLayout.getParent();
        if (parent != null) {
            ViewGroup.LayoutParams lp = resLayout.getLayoutParams();
            LinearLayout parentLayout = new LinearLayout(resLayout.getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            parentLayout.setLayoutParams(lp);
            ViewGroup group = (ViewGroup) parent;
            int index = group.indexOfChild(resLayout);
            group.removeView(resLayout);
            group.addView(parentLayout, index, lp);
            parentLayout.addView(resLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            return parentLayout;
        }
        return null;
    }

    /**
     * return    false 在顶部
     * true  不在顶部
     */
    public static boolean isVerticallyScrollUp(View mTargetScrollView) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTargetScrollView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTargetScrollView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mTargetScrollView, -1) || mTargetScrollView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTargetScrollView, -1);
        }
    }

    /**
     * return    false 在底部
     * true  不在底部
     */
    public static boolean isVerticallyScrollDown(View mTargetScrollView) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTargetScrollView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTargetScrollView;
                int count = absListView.getAdapter().getCount();
                int fristPos = absListView.getFirstVisiblePosition();
                if (fristPos == 0 && absListView.getChildAt(0).getTop() >= absListView.getPaddingTop()) {
                    return false;
                }
                int lastPos = absListView.getLastVisiblePosition();
                return lastPos > 0 && count > 0 && lastPos == count - 1;
            } else {
                return ViewCompat.canScrollVertically(mTargetScrollView, 1) || mTargetScrollView.getScrollY() < 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTargetScrollView, 1);
        }
    }
}
