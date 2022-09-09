package com.github.yangkl.lib.http_logger.service

data class RequestResult(
    val string: String? = null,
    var throwable: Throwable? = null
)