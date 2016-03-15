package zsx.com.test.ui.refresh;

import java.util.List;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/7 13:47
 */
public interface IAutoLoadMore<T> {
    boolean hasMoreData(int count);

    List<T> getList();
}
