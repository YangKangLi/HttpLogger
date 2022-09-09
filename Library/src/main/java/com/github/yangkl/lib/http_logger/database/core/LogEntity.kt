package com.github.yangkl.lib.http_logger.database.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_log")
data class LogEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "log_id")
    val logId: Int? = null,

    @ColumnInfo(name = "log_content")
    val content: String? = null,

    @ColumnInfo(name = "create_time")
    val createTime: Long = System.currentTimeMillis()
)