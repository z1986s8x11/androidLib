package com.zsx.itf;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/9/9 10:54
 */
public interface Lib_OnBackKeyListener {
    /**
     * return true 植入onBackKey逻辑   拦截   返回键操作
     * return false 植入onBackKey逻辑  不拦截 返回键操作
     */
    boolean onBackKey();
}
