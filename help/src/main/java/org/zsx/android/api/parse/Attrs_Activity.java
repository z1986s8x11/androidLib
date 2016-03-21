package org.zsx.android.api.parse;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.TextView;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class Attrs_Activity extends _BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parse_attrs);
    }
}

class CustomView extends TextView {

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.lib_TextView);
        int n = typedArray.getIndexCount();
        setText("");
        for (int i = 0; i < n; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.lib_TextView_testBoolean:
                    append("testBoolean :" + String.valueOf(typedArray.getBoolean(index, false)));
                    break;
                case R.styleable.lib_TextView_testColor:
                    append("testColor :" + String.valueOf(typedArray.getColorStateList(index).getDefaultColor()));
                    break;
                case R.styleable.lib_TextView_testColorReference:
                    append("testColorReference :" + String.valueOf(typedArray.getColorStateList(index).getDefaultColor()));
                    break;
                case R.styleable.lib_TextView_testDimension:
                    append("testDimension :" + String.valueOf(typedArray.getDimensionPixelSize(index, -1)));
                    break;
                case R.styleable.lib_TextView_testEnum:
                    append("testEnum :" + typedArray.getInt(R.styleable.lib_TextView_testEnum, -1));
                    break;
                case R.styleable.lib_TextView_testInteger:
                    append("testInteger :" + String.valueOf(typedArray.getInt(index, -1)));
                    break;
                case R.styleable.lib_TextView_testFloat:
                    append("testFloat :" + String.valueOf(typedArray.getFloat(index, -1)));
                    break;
                case R.styleable.lib_TextView_testReference:
                    append("testReference :" + typedArray.getText(index));
                    break;
            }
            append("\n");
        }
        typedArray.recycle();
    }

}
