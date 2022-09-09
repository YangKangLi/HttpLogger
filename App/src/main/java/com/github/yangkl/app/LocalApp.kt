package com.github.yangkl.app

import android.app.Application
import com.github.yangkl.lib.http_logger.HttpLogger
import com.github.yangkl.lib.http_logger.service.RequestResult

class LocalApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // 初始化HttpLogger
        HttpLogger.init(this).debug(true).url("").callback(httpLoggerCallback)

    }

    // HttpLogger的Callback
    private val httpLoggerCallback = object : HttpLogger.Callback {
        override fun getHeader(): Map<String, String>? {
            return null
        }

        override fun getParseResult(result: RequestResult): HttpLogger.ParseResult {
            return HttpLogger.ParseResult(true)
        }
    }
}