package com.zsx.util;

import android.graphics.Color;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/15 17:20
 */
public class _ColorsUtil {
    /**
     * 颜色加深处理
     *
     * @param ARGBValue RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    public static int _deepenColor(int ARGBValue) {
        int alpha = ARGBValue >> 24 & 0xFF;
        int red = ARGBValue >> 16 & 0xFF;
        int green = ARGBValue >> 8 & 0xFF;
        int blue = ARGBValue & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.argb(alpha, red, green, blue);
    }
}
