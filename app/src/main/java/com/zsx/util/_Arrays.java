package com.zsx.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/2/1 17:19
 */
public final class _Arrays {
    private _Arrays() {
    }

    public static <T> List<T> asList(T... t) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, t);
        return list;
    }

    public static boolean isEmpty(List<?> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static <T> List<T> filter(List<T> list, Filter<T> filter) {
        List<T> temp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            if (!filter.isFilter(t)) {
                temp.add(t);
            }
        }
        return temp;
    }

    public interface Filter<T> {
        boolean isFilter(T t);
    }
}

