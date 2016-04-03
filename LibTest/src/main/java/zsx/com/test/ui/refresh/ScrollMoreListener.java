package zsx.com.test.ui.refresh;

import android.widget.AbsListView;

import com.zsx.itf.Lib_ListDataAdapter;
import com.zsx.network.Lib_BaseHttpRequestData;
import com.zsx.network.Lib_HttpResult;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/11 11:47
 */
//public class ScrollMoreListener<Id, Result extends Lib_ListDataAdapter, Parameter> implements AbsListView.OnScrollListener {
//    private Lib_BaseHttpRequestData<Id, Lib_ListDataAdapter, Parameter> mLoadData;
//    private boolean mLastItemVisible;
//    private ReadDataListener readDataListener;
//
//    public ScrollMoreListener(Lib_BaseHttpRequestData<Id, Lib_ListDataAdapter, Parameter> loadData, ReadDataListener readDataListener) {
//        this.mLoadData = loadData;
//        this.readDataListener = readDataListener;
//    }
//
//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        if (readDataListener != null) {
//            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLastItemVisible) {
//                if (!mLoadData._isLoading()) {
//                    if (mLoadData != null) {
//                        if (mLoadData._hasCache()) {
//                            Lib_HttpResult<Lib_ListDataAdapter> result = mLoadData._getLastData();
//                            if (result.getData().hasMoreListData()) {
//                                readDataListener.readData(result.getCurrentCount());
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        mLastItemVisible = (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
//    }
//
//    public interface ReadDataListener {
//        void readData(int totalCount);
//    }
//}
