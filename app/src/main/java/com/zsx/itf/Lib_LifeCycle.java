package com.zsx.itf;

import java.util.Set;

/**
 * Created by zhusx on 2015/8/28.
 */
public interface Lib_LifeCycle {
    void _addOnCycleListener(Lib_OnCycleListener listener);

    void _removeOnCycleListener(Lib_OnCycleListener listener);

    Set<Lib_OnCycleListener> getCycleListeners();
}
