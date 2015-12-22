package zsx.com.test.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/12/22.
 */
public class ShapeTextView extends TextView {
    /**
     <selector xmlns:android="http://schemas.android.com/apk/res/android">
         <item android:state_enabled="true">
             <shape>
                 <corners android:bottomLeftRadius="" android:bottomRightRadius="" android:radius="" android:topLeftRadius="" android:topRightRadius=""/>
                 <gradient android:angle="" android:centerColor="" android:centerX="" android:centerY="" android:endColor=""
                 android:gradientRadius="" android:startColor="" android:type="" android:useLevel=""/>
                 <padding android:bottom="" android:left="" android:right="" android:top=""/>
                 <size android:width="" android:height=""/>
                 <solid android:color=""/>
                 <stroke android:color="" android:dashGap="" android:dashWidth="" android:width=""/>
             </shape>
         </item>
     </selector>
     * @param context
     */
    public ShapeTextView(Context context) {
        super(context);
        init(context, null);
    }

    public ShapeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShapeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShapeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, com.zsx.R.styleable.Lib_ShapeTextView);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int index = typedArray.getIndex(i);
            switch (index){
                case com.zsx.R.styleable.Lib_ShapeTextView_bottomLeftRadius:
                    break;
            }
        }
        typedArray.recycle();
    }
}
