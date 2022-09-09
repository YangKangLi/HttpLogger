package com.github.yangkl.lib.http_logger.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.github.yangkl.lib.http_logger.HttpLogger
import com.github.yangkl.lib.http_logger.database.core.LogDatabase
import com.github.yangkl.lib.http_logger.database.core.LogDatabase.Companion.DATABASE_NAME
import com.github.yangkl.lib.http_logger.database.core.LogEntity

class DbMgr {

    private var database: LogDatabase? = null


    // 单例持有对象
    private object SingletonHolder {
        val holder = DbMgr()
    }

    /**
     * 初始化
     */
    fun init(context: Context?) {
        context?.let {
            database = Room.databaseBuilder(it, LogDatabase::class.java, DATABASE_NAME).build()
        } ?: kotlin.run {
            Log.e(HttpLogger.TAG, "初始化数据库失败：context不能为null")
        }
    }

    /**
     * 判断是否初始化
     */
    fun isInitialized(): Boolean {
        return database != null
    }

    /**
     * 插入日志记录
     */
    fun insertLogs(entity: LogEntity) {
        database?.getLogDao()?.insert(entity)
    }

    /**
     * 删除日志记录
     */
    fun deleteLog(entity: LogEntity) {
        database?.getLogDao()?.deleteLogs(entity)
    }

    /**
     * 查询第一条日志记录
     */
    fun queryFirstLog(): LogEntity? {
        return database?.getLogDao()?.queryFirstLog()
    }


    ///////////////////////////////////////////////////////////////////////////
    /// 伴生对象
    ///////////////////////////////////////////////////////////////////////////

    companion object {


        // 获得单例对象
        fun getInstance(): DbMgr {
            return SingletonHolder.holder
        }
    }
}