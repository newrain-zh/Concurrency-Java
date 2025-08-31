package com.newrain.concurrency.thread.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 继承ThreadPoolExecutor类，覆盖了shutdown(),
 * shutdownNow(),
 * beforeExecute()
 * 和 afterExecute()
 * 方法来统计线程池的执行情况
 *
 * @author newRain
 * @description 线程池监控示例
 */
public class DPoolMonitor extends ThreadPoolExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(DPoolMonitor.class);

    /**
     * 保存任务开始执行的时间，当任务结束时，用任务结束时间减去开始时间计算任务执行时间
     */
    private ConcurrentSkipListMap<String, Long> startTimes;

    // 保存任务执行时间
    private List<Long> taskTimes;

    /**
     * 线程池名称，一般以业务名称命名，方便区分
     */
    private final        String        poolName;
    private static final AtomicInteger threadInitNumber = new AtomicInteger(1);

    /**
     * 调用父类的构造方法，并初始化HashMap和线程池名称
     *
     * @param corePoolSize    线程池核心线程数
     * @param maximumPoolSize 线程池最大线程数
     * @param keepAliveTime   线程的最大空闲时间
     * @param unit            空闲时间的单位
     * @param workQueue       保存被提交任务的队列
     * @param poolName        线程池名称
     */
    public DPoolMonitor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, String poolName) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new EventThreadFactory(poolName), poolName);
        allowCoreThreadTimeOut(true);
        this.taskTimes  = Collections.synchronizedList(new ArrayList<>(10));
        this.startTimes = new ConcurrentSkipListMap<>();
        /*Thread thread = new Thread(() -> {
            while (true) {
//                LOG.info("shutDown={},isTerminated={},isTerminating={}", this.isShutdown(), this.isTerminated(), isTerminating());
                LOG.info("threadInitNumber={}", threadInitNumber);
                // 检查队列是否已满
                if (this.getQueue().remainingCapacity() <= 100) {
                    LOG.warn("Task queue is full. Current queue size: {}", this.getQueue().size());
                    this.setDynamicCorePoolSize(maximumPoolSize);
                    this.setDynamicMaximumPoolSize(maximumPoolSize + 20);
                    LOG.info("调整后参数，核心线程:{} 最大线程数:{}", this.getCorePoolSize(), this.getMaximumPoolSize());
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();*/
    }


    /**
     * 调用父类的构造方法，并初始化HashMap和线程池名称
     *
     * @param corePoolSize    线程池核心线程数
     * @param maximumPoolSize 线程池最大线程数
     * @param keepAliveTime   线程的最大空闲时间
     * @param unit            空闲时间的单位
     * @param workQueue       保存被提交任务的队列
     * @param threadFactory   线程工厂
     * @param poolName        线程池名称
     */
    public DPoolMonitor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, String poolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.poolName   = poolName;
        this.taskTimes  = Collections.synchronizedList(new ArrayList<>(10));
        this.startTimes = new ConcurrentSkipListMap<>();
    }

    /**
     * 线程池延迟关闭时（等待线程池里的任务都执行完毕），统计线程池情况
     */
    @Override
    public void shutdown() {
        // 统计已执行任务、正在执行任务、未执行任务数量
        LOG.info("{} Going to shutdown. Executed tasks: {}, Running tasks: {}, Pending tasks: {}", this.poolName, this.getCompletedTaskCount(), this.getActiveCount(), this.getQueue().size());
        super.shutdown();
    }

    /**
     * 线程池立即关闭时，统计线程池情况
     */
    @Override
    public List<Runnable> shutdownNow() {
        // 统计已执行任务、正在执行任务、未执行任务数量
        LOG.info("{} Going to immediately shutdown. Executed tasks: {}, Running tasks: {}, Pending tasks: {}", this.poolName, this.getCompletedTaskCount(), this.getActiveCount(), this.getQueue().size());
        return super.shutdownNow();
    }

    /**
     * 任务执行之前，记录任务开始时间
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        startTimes.put(String.valueOf(r.hashCode()), System.currentTimeMillis());
    }

    /**
     * 任务执行之后，计算任务结束时间
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Long startTime = startTimes.get(String.valueOf(r.hashCode()));
        long now       = System.currentTimeMillis();
        long diff      = (now - startTime);
        this.taskTimes.add(diff);
        threadInitNumber.incrementAndGet();
        // 统计任务耗时、初始线程数、核心线程数、正在执行的任务数量、
        // 已完成任务数量、任务总数、队列里缓存的任务数量、池中存在的最大线程数、
        // 最大允许的线程数、线程空闲时间、线程池是否关闭、线程池是否终止
        LOG.info("{}-pool-monitor: " + "Duration: {} ms," + " 线程数: {}," + " 核心线程数: {}, " + "活跃线程: {}, " + "Completed: {}, 任务数: {}, 队列容量大小: {}, LargestPoolSize: {}, " + "MaximumPoolSize: {},  KeepAliveTime: {}, isShutdown: {}, isTerminated: {}", this.poolName, diff, this.getPoolSize(), this.getCorePoolSize(), this.getActiveCount(), this.getCompletedTaskCount(), this.getTaskCount(), this.getQueue().remainingCapacity(), this.getLargestPoolSize(), this.getMaximumPoolSize(), this.getKeepAliveTime(TimeUnit.MILLISECONDS), this.isShutdown(), this.isTerminated());

    }

    /**
     * 动态修改核心线程数
     */
    public void setDynamicCorePoolSize(int corePoolSize) {
        if (corePoolSize < 0) {
            throw new IllegalArgumentException("核心线程数不能为负数");
        }
        super.setCorePoolSize(corePoolSize);
    }

    /**
     * 动态修改最大线程数
     */
    public void setDynamicMaximumPoolSize(int maximumPoolSize) {
        if (maximumPoolSize < super.getCorePoolSize()) {
            throw new IllegalArgumentException("最大线程数不能小于核心线程数");
        }
        super.setMaximumPoolSize(maximumPoolSize);
    }

    @Override
    public void allowCoreThreadTimeOut(boolean value) {
        super.allowCoreThreadTimeOut(value);
    }

    /**
     * 动态修改队列容量（核心难点）
     */
    public void setDynamicQueueCapacity(int newCapacity) {
        if (newCapacity < 0) {
            throw new IllegalArgumentException("队列容量不能为负数");
        }
    }


    /**
     * 生成线程池所用的线程，只是改写了线程池默认的线程工厂，传入线程池名称，便于问题追踪
     */
    static class EventThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber   = new AtomicInteger(1);
        private final        ThreadGroup   group;
        private final        AtomicInteger threadNumber = new AtomicInteger(1);
        private final        String        namePrefix;

        /**
         * 初始化线程工厂
         *
         * @param poolName 线程池名称
         */
        EventThreadFactory(String poolName) {
            group      = Thread.currentThread().getThreadGroup();
            namePrefix = poolName + "-pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    public static void main(String[] args) {
     testCoreSize();
    }
    public static void testPool(){
        DPoolMonitor poolMonitor = new DPoolMonitor(10, 20, 50, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), "monitor thread");
        for (int i = 0; i < 1000; i++) {
            System.out.println("执行" + i);
            Runnable runnable = () -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    LOG.error("InterruptedException", e);
                }
            };
            try {
                poolMonitor.execute(runnable);
            }catch (Exception e){

            }
        }
        LOG.info("总共执行了:{}任务", threadInitNumber);
        poolMonitor.shutdown();
    }

    public static void testCoreSize(){
        DPoolMonitor poolMonitor = new DPoolMonitor(10, 20, 50, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), "monitor thread");
        poolMonitor.setCorePoolSize(50);
        LOG.info("coreSize={}",poolMonitor.getCorePoolSize());
    }
}