package com.zsx.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/2/1.17:19
 */
public final class _Arrays {
    private _Arrays() {
    }

    public static <T> List<T> asList(T... t) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < t.length; i++) {
            list.add(t[i]);
        }
        return list;
    }
}
