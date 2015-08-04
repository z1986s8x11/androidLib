package com.zsx.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

public class Lib_Widget_CleanableEditText extends EditText {
    private Drawable mRightDrawable;
    private Drawable InvisibleDrawable;
    private boolean isHasFocus;

    public Lib_Widget_CleanableEditText(Context context) {
        super(context);
        init();
    }

    public Lib_Widget_CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Lib_Widget_CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Drawable[] drawables = this.getCompoundDrawables();
        mRightDrawable = drawables[2];
        InvisibleDrawable = new InvisibleDrawable(this);
        InvisibleDrawable.setBounds(mRightDrawable.getBounds());
        this.setOnFocusChangeListener(new FocusChangeListenerImpl());
        this.addTextChangedListener(new TextWatcherImpl());
        setClearDrawableVisible(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight()))
                        && (event.getX() < (getWidth() - getPaddingRight()));
                if (isClean) {
                    setText("");
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private class FocusChangeListenerImpl implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            isHasFocus = hasFocus;
            if (isHasFocus) {
                boolean isVisible = !TextUtils.isEmpty(getText().toString().trim());
                setClearDrawableVisible(isVisible);
            } else {
                setClearDrawableVisible(false);
            }
        }

    }

    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            if (isHasFocus) {
                boolean isVisible = !TextUtils.isEmpty(getText().toString().trim());
                setClearDrawableVisible(isVisible);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

    }

    protected void setClearDrawableVisible(boolean isVisible) {
        if (isVisible) {
            setCompoundDrawables(getCompoundDrawables()[0],
                    getCompoundDrawables()[1], mRightDrawable,
                    getCompoundDrawables()[3]);
        } else {
            setCompoundDrawables(getCompoundDrawables()[0],
                    getCompoundDrawables()[1], InvisibleDrawable,
                    getCompoundDrawables()[3]);
        }
    }

    public void setShakeAnimation() {
        this.clearAnimation();
        this.setAnimation(shakeAnimation(5));
    }

    public Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    final class InvisibleDrawable extends Drawable {
        InvisibleDrawable(Lib_Widget_CleanableEditText paramEditTextWithClearButton) {
        }

        public final void draw(Canvas paramCanvas) {
        }

        public final int getOpacity() {
            return 0;
        }

        public final void setAlpha(int paramInt) {
        }

        public final void setColorFilter(ColorFilter paramColorFilter) {
        }
    }
}