package com.ls.common.basics.util;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {

    private static volatile ThreadPoolUtil mInstance;
    //核心线程池的数量，同时能够执行的线程数量
    private int corePoolSize;
    //最大线程池数量，表示当缓冲队列满的时候能继续容纳的等待任务的数量
    private int maxPoolSize;
    //存活时间
    private long keepAliveTime = 1;
    //时间单位
    private TimeUnit unit = TimeUnit.HOURS;

    private ThreadPoolExecutor executor;

    private ThreadPoolUtil() {
        //给corePoolSize赋值：当前设备可用处理器核心数*2 + 1,能够让cpu的效率得到最大程度执行（有研究论证的）
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        maxPoolSize = corePoolSize;
        executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                unit,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryBuilder()
                        .setNamePrefix("thread-pool-").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public static ThreadPoolUtil getPool(){
        if (mInstance == null) {
            synchronized (ThreadPoolUtil.class) {
                if (mInstance == null) {
                    mInstance = new ThreadPoolUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * <描述功能> 执行线程
     * @author lang
     * @date 10:24 2021/12/3
     * @param runnable 单个线程
     * @return void
     **/
    public void execute(Runnable runnable){
        if(null != runnable){
            executor.execute(runnable);
        }
    }
}
