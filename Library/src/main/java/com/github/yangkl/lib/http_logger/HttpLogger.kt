package com.github.yangkl.lib.http_logger

import android.app.Application
import android.text.TextUtils
import android.util.Log
import com.github.yangkl.lib.http_logger.core.Logger
import com.github.yangkl.lib.http_logger.database.DbMgr
import com.github.yangkl.lib.http_logger.database.core.LogEntity
import com.github.yangkl.lib.http_logger.service.HttpService
import com.github.yangkl.lib.http_logger.service.RequestResult
import com.github.yangkl.lib.http_logger.utils.ThreadUtils

class HttpLogger {

    companion object {

        const val TAG = "HttpLogger"

        // 获得单例对象
        @JvmStatic
        fun init(context: Application): HttpLogger {
            return SingletonHolder.holder.init(context)
        }

        @JvmStatic
        fun d(content: String) {
            if (Logger.getInstance().check()) {
                if (Logger.getInstance().debug) {
                    Log.d(TAG, content)
                }
                Logger.getInstance().writeAndUploadLog(content)
            }
        }

        @JvmStatic
        fun w(content: String) {
            if (Logger.getInstance().check()) {
                if (Logger.getInstance().debug) {
                    Log.w(TAG, content)
                }
                Logger.getInstance().writeAndUploadLog(content)
            }
        }

        @JvmStatic
        fun e(content: String) {
            if (Logger.getInstance().check()) {
                if (Logger.getInstance().debug) {
                    Log.e(TAG, content)
                }
                Logger.getInstance().writeAndUploadLog(content)
            }
        }
    }

    // 单例持有对象
    private object SingletonHolder {
        val holder = HttpLogger()
    }

    /**
     * 初始化
     */
    private fun init(context: Application): HttpLogger {
        Logger.getInstance().context = context
        return this
    }

    /**
     * 设置debug模式
     */
    fun debug(debug: Boolean): HttpLogger {
        Logger.getInstance().debug = debug
        return this
    }

    /**
     * 设置上报日志的地址
     */
    fun url(url: String): HttpLogger {
        Logger.getInstance().url = url
        return this
    }

    fun callback(callback: Callback): HttpLogger {
        Logger.getInstance().callback = callback
        return this
    }


    /**
     * 回调
     */
    interface Callback {

        /**
         * 获得Header
         */
        fun getHeader(): Map<String, String>?

        /**
         * 将请求Http接口返回的数据交由客户端处理
         */
        fun getParseResult(result: RequestResult): ParseResult
    }

    /**
     * 解析结果
     */
    data class ParseResult(val success: Boolean, val milliseconds: Long = 0)
}

