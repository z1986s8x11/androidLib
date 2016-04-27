package org.zsx.android.api.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.zsx.android.base._BaseFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/27 10:16
 */
public class ThreadPoolExecutor_Fragment extends _BaseFragment implements View.OnClickListener {
    /**
     * corePoolSize： 线程池维护线程的最少数量
     * maximumPoolSize：线程池维护线程的最大数量
     * keepAliveTime： 线程池维护线程所允许的空闲时间
     * unit： 线程池维护线程所允许的空闲时间的单位
     * workQueue： 线程池所使用的缓冲队列
     * handler： 线程池对拒绝任务的处理策略
     */
    //提交执行数 = 线程池最大数(执行) + 缓存队列长度(待执行)
    ExecutorService executor = new ThreadPoolExecutor(1, 2, 0,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(3), Executors.defaultThreadFactory(),
            new RejectedExecutionHandler() {

                @Override
                public void rejectedExecution(Runnable arg0,
                                              ThreadPoolExecutor arg1) {
                    //在执行线程 执行
                    //被拒绝之后会回调此方法
                }
            });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView t = new TextView(inflater.getContext());
        t.setOnClickListener(this);
        return t;
    }

    @Override
    public void onClick(View v) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //执行线程  如果被拒绝回执行 rejectedExecution方法
            }
        });
    }
}
