package com.zsx.itf;

/**
 * 包含实现空试图
 * Created by zhusx on 2015/9/25.
 */
public interface Lib_EmptyViewInterface {
    void showLoadingView();

    void showLoadErrorView(String errorMessage);

    void showLoadComplete();
}
