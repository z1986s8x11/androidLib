package zsx.com.test.ui.refresh;

import com.zsx.util._Arrays;

import java.util.List;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/7 16:03
 */
public class DataEntity implements IAutoLoadMore {
    public int i = 0;

    @Override
    public boolean hasMoreData(int count) {
        if (i < 5) {
            return true;
        }
        return false;
    }

    @Override
    public List<String> getList() {
        return _Arrays.asList(String.valueOf(i * 5), String.valueOf(i * 5 + 1), String.valueOf(i * 5 + 2), String.valueOf(i * 5 + 3), String.valueOf(i * 5 + 4));
    }
}
