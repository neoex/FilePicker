package cn.mugua.com.filepicker.util;


import android.support.annotation.NonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

 class BasicThreadFactory implements ThreadFactory {
    private AtomicLong mThreadCounter;
    /**
     * 包装工厂
     */
    private ThreadFactory mThreadFactory;
    /**
     * 非捕获异常处理器
     */
    private Thread.UncaughtExceptionHandler mExceptionHandler;
    /**
     * 命名模式
     */
    private String mNamingPattern;
    /**
     * 优先级
     */
    private Integer mPriority;
    /**
     * 后台状态标识
     */
    private Boolean mDaemonFlag;

    public BasicThreadFactory(ThreadBuilder threadBuilder) {
        if (threadBuilder instanceof DefaultThreadBuilder) {
            DefaultThreadBuilder builder = (DefaultThreadBuilder) threadBuilder;
            if (builder.mThreadFactory == null) {
                mThreadFactory = Executors.defaultThreadFactory();
            } else {
                mThreadFactory = builder.mThreadFactory;
            }
            mNamingPattern = builder.mNamingPattern;
            mDaemonFlag = builder.mDaemonFlag;
            mExceptionHandler = builder.mExceptionHandler;
            mPriority = builder.mPriority;
            mThreadCounter = new AtomicLong();
        }
    }

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        Thread thread = getWrappedFactory().newThread(runnable);
        initThread(thread);
        return thread;
    }


    private void initThread(Thread thread) {
        if (getNamingPattern() != null) {
            Long count = mThreadCounter.incrementAndGet();
            thread.setName(String.format(getNamingPattern(), count));
        }
        if (getUncaughtExceptionHandler() != null) {
            thread.setUncaughtExceptionHandler(getUncaughtExceptionHandler());
        }
        if (getPriority() != null) {
            thread.setPriority(getPriority());
        }
        if (getDaemonFlag() != null) {
            thread.setDaemon(getDaemonFlag());
        }
    }

    /**
     * 获取包装工厂
     *
     * @return 不会返回null
     */
    public final ThreadFactory getWrappedFactory() {
        return mThreadFactory;
    }

    /**
     * 获取命名模式
     *
     * @return
     */
    public final String getNamingPattern() {
        return mNamingPattern;
    }

    /**
     * 获取是否为后台线程标识
     *
     * @return
     */
    public final Boolean getDaemonFlag() {
        return mDaemonFlag;
    }

    /**
     * 获取优先级
     *
     * @return
     */
    public final Integer getPriority() {
        return mPriority;
    }

    /**
     * 获取非捕获异常处理器
     *
     * @return
     */
    public final Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return mExceptionHandler;
    }

    /**
     * 获取创建的线程数量
     *
     * @return
     */
    public long getThreadCount() {
        return mThreadCounter.get();
    }

    public static final class DefaultThreadBuilder implements ThreadBuilder<BasicThreadFactory> {
        /**
         * 包装工厂
         */
        private ThreadFactory mThreadFactory;
        /**
         * 非捕获异常处理器
         */
        private Thread.UncaughtExceptionHandler mExceptionHandler;
        /**
         * 命名模式
         */
        private String mNamingPattern;
        /**
         * 优先级
         */
        private Integer mPriority;
        /**
         * 后台状态标识
         */
        private Boolean mDaemonFlag;

        @Override
        public BasicThreadFactory build() {
            BasicThreadFactory basicThreadFactory = new BasicThreadFactory(this);
            reset();
            return basicThreadFactory;
        }

        /**
         * 创建包装工厂
         */
        public DefaultThreadBuilder wrappedFactory(ThreadFactory threadFactory) {
            if (threadFactory == null) {
                throw new NullPointerException("wrappedFactory can not to be null !");
            }
            mThreadFactory = threadFactory;
            return this;
        }

        /**
         * 设置命名模式
         */
        public DefaultThreadBuilder namingPattern(String namingPattern) {
            if (namingPattern == null) {
                throw new NullPointerException("namingPattern can not to be null !");
            }
            mNamingPattern = namingPattern;
            return this;
        }

        /**
         * 设置非捕获异常处理
         */
        public DefaultThreadBuilder uncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            if (uncaughtExceptionHandler == null) {
                throw new NullPointerException("uncaughtExceptionHandler can not to be null !");
            }
            mExceptionHandler = uncaughtExceptionHandler;
            return this;
        }

        /**
         * 设置优先级
         */
        public DefaultThreadBuilder priority(int priority) {
            mPriority = priority;
            return this;
        }

        /**
         * 设置后台标识
         */
        public DefaultThreadBuilder daemon(boolean daemonFlag) {
            mDaemonFlag = daemonFlag;
            return this;
        }

        /**
         * 重置
         */
        public void reset() {
            mExceptionHandler = null;
            mDaemonFlag = null;
            mPriority = null;
            mNamingPattern = null;
            mThreadFactory = null;
        }

    }


}
