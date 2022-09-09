package com.github.yangkl.lib.http_logger.database.core

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LogEntity::class], version = LogDatabase.DATABASE_VERSION, exportSchema = false)
abstract class LogDatabase : RoomDatabase() {

    abstract fun getLogDao(): LogDao

    companion object {
        // 数据库文件名
        const val DATABASE_NAME = "http_logger.db"

        // 数据库版本
        const val DATABASE_VERSION = 1
    }
}