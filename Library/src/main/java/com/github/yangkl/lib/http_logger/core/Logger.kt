package com.github.yangkl.lib.http_logger.core

import android.app.Application
import android.text.TextUtils
import android.util.Log
import com.github.yangkl.lib.http_logger.HttpLogger
import com.github.yangkl.lib.http_logger.database.DbMgr
import com.github.yangkl.lib.http_logger.database.core.LogEntity
import com.github.yangkl.lib.http_logger.service.HttpService
import com.github.yangkl.lib.http_logger.utils.ThreadUtils

class Logger {


    var context: Application? = null

    var debug: Boolean = false

    var url: String? = null

    var callback: HttpLogger.Callback? = null

    // 单例持有对象
    private object SingletonHolder {
        val holder = Logger()
    }

    fun check(): Boolean {
        if (getInstance().context == null) {
            Log.e(HttpLogger.TAG, "请先初始化HttpLogger!!")
            return false
        }

        if (TextUtils.isEmpty(getInstance().url)) {
            Log.e(HttpLogger.TAG, "请先设置URL!!")
            return false
        }

        return true
    }

    /**
     * 写入数据库并上报到服务
     */
    fun writeAndUploadLog(content: String) {
        ThreadUtils.execute {
            if (!DbMgr.getInstance().isInitialized()) {
                DbMgr.getInstance().init(context)
            }
            val entity = LogEntity(content = content)
            DbMgr.getInstance().insertLogs(entity)
            // 启动HttpService
            HttpService.startService(context!!)
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///  伴生对象
    ///////////////////////////////////////////////////////////////////////////////////////////////

    companion object {

        /**
         * 获得单例对象
         */
        fun getInstance(): Logger {
            return SingletonHolder.holder
        }
    }
}