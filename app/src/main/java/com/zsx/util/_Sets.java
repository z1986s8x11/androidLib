package com.zsx.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/7/13 11:08
 */
public class _Sets {
    public static <E> Set<E> newHashMap(E... item) {
        Set<E> set = new HashSet<>();
        Collections.addAll(set, item);
        return set;
    }

    public static boolean isEmpty(Set set) {
        if (set == null) {
            return true;
        }
        return set.isEmpty();
    }
}
