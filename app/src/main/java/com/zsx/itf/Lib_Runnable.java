package com.zsx.itf;

/**
 * Created by Administrator on 2016/3/16.
 */
public abstract class Lib_Runnable implements Runnable {
    private boolean isCancel;

    public boolean _isCancel() {
        return isCancel;
    }

    public void _setCancel() {
        this.isCancel = true;
    }
}
