package com.zsx.itf;

/**
 * Created by zhusx on 2015/8/28.
 */
public interface Lib_LifeCycle {
    void _addOnCancelListener(Lib_OnCancelListener listener);

    void _removeOnCancelListener(Lib_OnCancelListener listener);

    void _addOnCycleListener(Lib_OnCycleListener listener);

    void _removeOnCycleListener(Lib_OnCycleListener listener);
}
