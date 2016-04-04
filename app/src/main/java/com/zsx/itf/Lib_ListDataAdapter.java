package com.zsx.itf;

import java.util.List;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/11 16:08
 */
public interface Lib_ListDataAdapter {
    boolean hasMore(int lastIndexPage);

    List getListData();
}
