package com.github.yangkl.lib.http_logger.utils

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ThreadUtils {

    private object SingletonHolder {
        val INSTANCE = ThreadUtils()
    }

    init {
        executor = ThreadPoolExecutor(
            1,  //当某个核心任务执行完毕，会依次从缓冲队列中取出等待任务
            MAXIMUM_POOL_SIZE,  //5,先corePoolSize,然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
            KEEP_ALIVE_TIME,  //表示的是maximumPoolSize当中等待任务的存活时间
            TimeUnit.SECONDS,
            LinkedBlockingQueue(),  //缓冲队列，用于存放等待任务，Linked的先进先出
            Executors.defaultThreadFactory(),  //创建线程的工厂
            ThreadPoolExecutor.AbortPolicy() //用来对超出maximumPoolSize的任务的处理策略
        )
    }

    /**
     * 执行任务
     *
     * @param runnable
     */
    private fun doExecute(runnable: Runnable) {
        executor?.execute(runnable)
    }

    companion object {
        private const val MAXIMUM_POOL_SIZE = Int.MAX_VALUE
        private const val KEEP_ALIVE_TIME = (10 * 60).toLong()
        private var executor: ThreadPoolExecutor? = null

        /**
         * 对外提供的接口，用于执行任务
         *
         * @param runnable
         */
        fun execute(runnable: Runnable) {
            SingletonHolder.INSTANCE.doExecute(runnable)
        }
    }
}