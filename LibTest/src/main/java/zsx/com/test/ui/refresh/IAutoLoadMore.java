package zsx.com.test.ui.refresh;

import java.util.List;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/7 13:47
 */
public interface IAutoLoadMore<T> {
    /**
     * @param lastCount 最后一次请求的count(页码或者是个数)
     */
    boolean hasMoreData(int lastCount);

    /**
     * 拿到解析的List 列表
     */
    List<T> getList();
}
