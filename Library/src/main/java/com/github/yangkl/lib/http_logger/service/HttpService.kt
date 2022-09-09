package com.github.yangkl.lib.http_logger.service

import android.app.ActivityManager
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import com.github.yangkl.lib.http_logger.HttpLogger
import com.github.yangkl.lib.http_logger.core.Logger
import com.github.yangkl.lib.http_logger.database.DbMgr
import com.github.yangkl.lib.http_logger.utils.HttpUtils

class HttpService : IntentService("HttpService") {


    override fun onHandleIntent(intent: Intent?) {
        while (true) {
            val entity = DbMgr.getInstance().queryFirstLog()
            if (entity != null) {
                if (TextUtils.isEmpty(entity.content)) {
                    DbMgr.getInstance().deleteLog(entity)
                } else {
                    val result = HttpUtils.postJson(Logger.getInstance().url!!, entity.content!!, null)
                    val parseResult = Logger.getInstance().callback?.getParseResult(result)
                    if (parseResult != null) {
                        if (parseResult.success) {
                            DbMgr.getInstance().deleteLog(entity)
                        }

                        if (parseResult.milliseconds > 0) {
                            Thread.sleep(parseResult.milliseconds)
                        }
                    } else {
                        Log.d(HttpLogger.TAG, "没有得到解析结果")
                        break
                    }
                }
            } else {
                break;
            }
        }
    }

    companion object {
        fun startService(context: Context) {
            if (!isServiceRunning(context)) {
                try {
                    val intent = Intent(context, HttpService::class.java)
                    context.startService(intent)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }

        private fun isServiceRunning(context: Context): Boolean {
            val manager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
            val runningServices = manager.getRunningServices(Int.MAX_VALUE)
            for (item in runningServices) {
                if (HttpService::class.java.name == item.service.className) {
                    return true
                }
            }
            return false
        }
    }
}