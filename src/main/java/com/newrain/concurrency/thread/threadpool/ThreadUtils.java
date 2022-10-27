package com.newrain.concurrency.thread.threadpool;


import com.newrain.concurrency.thread.factory.MyThreadFactoryExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 关闭线程池代码
 *
 * @author newRain
 * @description 关闭线程池代码示例
 */
public class ThreadUtils {

    /**
     * 关闭线程池方法，这里只是给出了范例。具体的关闭流程和策略需要结合业务来去做处理。
     *
     * @param threadPool
     */
    public static void shutdownThreadPoolGracefully(ExecutorService threadPool) {
        //判断线程池是否关闭
        if (threadPool != null || threadPool.isTerminated()) {
            return;
        }
        //关闭线程池
        threadPool.shutdown();
        try {
            //等待60秒，让线程池中的任务执行关闭
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                //调用立即关闭，取消所有执行中的任务。
                threadPool.shutdownNow();
                //重试60S，如果还没关闭再次重试。
                if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.out.println("线程池中的任务未正常执行结束");
                }
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
        }
        //如果未关闭继续重试直到线程关闭
        if (!threadPool.isTerminated()) {
            try {
                for (int i = 0; i < 1000; i++) {
                    if (threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                        break;
                    }
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                //log Exception
            }
        }
    }

    /**
     * 利用钩子方法关闭
     */
    static class SeqOrScheduledTargetThreadPoolLazyHolder {
        static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(1, new MyThreadFactoryExample("seq"));

        static {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                shutdownThreadPoolGracefully(EXECUTOR);
            }));
        }
    }
}
