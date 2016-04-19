package org.zsx.android.api.widget;

import android.content.Context;
import android.view.ViewGroup;

import org.zsx.android.base._BaseActivity;

public class ViewGroup_Activity extends _BaseActivity {
    class myView extends ViewGroup {

        public myView(Context context) {
            super(context);
            //默认情况ViewGroup 不会调用onDraw. 设置setWillNotDraw(false) 之后,系统才会在postInvalidate();invalidate();后执行onDraw()
            //setWillNotDraw(false);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {

        }

        /**
         * PERSISTENT_NO_CACHE 说明不在内存中保存绘图缓存； PERSISTENT_ANIMATION_CACHE
         * 说明只保存动画绘图缓存； PERSISTENT_SCROLLING_CACHE 说明只保存滚动效果绘图缓存
         * PERSISTENT_ALL_CACHES 说明所有的绘图缓存都应该保存在内存中。
         */
        @Override
        public void setPersistentDrawingCache(int drawingCacheToKeep) {
            super.setPersistentDrawingCache(drawingCacheToKeep);
        }
    }
}
