package com.base.lib.http;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by malijie on 2017/8/4.
 */

public class DefaultThreadPool {
    private static final int BLOCK_QUEEN_SIZE = 20;
    private static final int THREAD_POOL_MAX_SIZE = 10;
    private static final int THREAD_POOL_SIZE = 6;

    private static ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(BLOCK_QUEEN_SIZE);
    private static DefaultThreadPool instance = null;

    static AbstractExecutorService pool = new ThreadPoolExecutor(
            THREAD_POOL_SIZE,
            THREAD_POOL_MAX_SIZE,
            15L,
            TimeUnit.SECONDS,
            blockingQueue,new ThreadPoolExecutor.DiscardOldestPolicy());

    public static DefaultThreadPool getInstance(){
        if (DefaultThreadPool.instance == null) {
            DefaultThreadPool.instance = new DefaultThreadPool();
        }
        return DefaultThreadPool.instance;
    }

    public  void removeAllTask(){
        blockingQueue.clear();
    }

    public  void removeTask(Object object){
        blockingQueue.remove(object);
    }

    public  void shutDown(){
        if(pool != null){
            pool.shutdown();
        }
    }

    public void waitTermination(){
        try {
            pool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void execute(Runnable r){
        if(r != null){
            pool.execute(r);
        }
    }

    public  void submit(Runnable r){
        if(r != null){
            pool.submit(r);
        }
    }

    /**
     * 关闭，立即关闭，并挂起所有正在执行的线程，不接受新任务
     */
    public  void shutdownRightNow() {
        if (DefaultThreadPool.pool != null) {
            DefaultThreadPool.pool.shutdownNow();
            try {
                // 设置超时极短，强制关闭所有任务
                DefaultThreadPool.pool.awaitTermination(0,
                        TimeUnit.MICROSECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
