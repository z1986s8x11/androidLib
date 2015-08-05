package zsx.com.test.base;

import android.content.Context;

import com.zsx.adapter.Lib_BaseAdapter;

import java.util.List;

/**
 * Created by zhusx on 2015/8/5.
 */
public abstract class _BaseAdapter<T> extends Lib_BaseAdapter<T> {
    public _BaseAdapter(Context context) {
        super(context);
    }

    public _BaseAdapter(Context context, List<T> list) {
        super(context, list);
    }
}
