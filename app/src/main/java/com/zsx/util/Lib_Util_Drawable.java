package com.zsx.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.DisplayMetrics;

public class Lib_Util_Drawable {
	/**
	 * 屏幕高度,单位像素(px).
	 */
	public static int getHeight(Context context) {
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		return displayMetrics.heightPixels;
	}

	public static ColorStateList getColorStateListForPress(int stateColor,
			int normalColor) {
		return getColorState(stateColor, normalColor,
				android.R.attr.state_pressed);
	}

	public static ColorStateList getColorStateListForSelected(int stateColor,
			int normalColor) {
		return getColorState(stateColor, normalColor,
				android.R.attr.state_selected);
	}

	public static ColorStateList getColorStateListForChecked(int stateColor,
			int normalColor) {
		return getColorState(stateColor, normalColor,
				android.R.attr.state_checked);
	}

	public static ColorStateList getColorState(int stateColor, int normalColor,
			Integer... attrStates) {
		int[][] states = new int[attrStates.length + 1][];
		for (int i = 0; i < attrStates.length; i++) {
			states[i] = new int[] { attrStates[i] };
		}
		states[attrStates.length] = new int[] {};
		int[] colors = new int[attrStates.length + 1];
		for (int i = 0; i < attrStates.length; i++) {
			colors[i] = stateColor;
		}
		colors[attrStates.length] = normalColor;
		ColorStateList colorStateList = new ColorStateList(states, colors);
		return colorStateList;
	}

	public static StateListDrawable getStateListDrawableForSelected(
			Drawable stateDrawable, Drawable normalDrawable) {
		return getStateListDrawable(stateDrawable, normalDrawable,
				android.R.attr.state_selected);
	}

	public static StateListDrawable getStateListDrawableForChecked(
			Drawable stateDrawable, Drawable normalDrawable) {
		return getStateListDrawable(stateDrawable, normalDrawable,
				android.R.attr.state_checked);
	}

	public static StateListDrawable getStateListDrawableForPressed(
			Drawable stateDrawable, Drawable normalDrawable) {
		return getStateListDrawable(stateDrawable, normalDrawable,
				android.R.attr.state_pressed);
	}

	public static StateListDrawable getStateListDrawable(
			Drawable stateDrawable, Drawable normalDrawable, int attrState) {
		StateListDrawable bg = new StateListDrawable();
		bg.addState(new int[] { attrState }, stateDrawable);
		bg.addState(new int[] { -attrState }, normalDrawable);
		bg.addState(new int[] {}, normalDrawable);
		return bg;
	}

	// GradientDrawable selectedTrue = new GradientDrawable();
	// selectedTrue.setColor(Color.parseColor(bean.color));
	// selectedTrue.setStroke(1, Color.WHITE);
	// GradientDrawable selectedFalse = new GradientDrawable();
	// selectedFalse.setColor(Color.WHITE);
	// selectedFalse.setStroke(1, Color.parseColor(bean.color));
	// tv.setBackgroundDrawable(getStateListDrawableForSelected(
	// selectedTrue, selectedFalse));
}
