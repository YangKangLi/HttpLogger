package com.github.yangkl.lib.http_logger.utils

import com.github.yangkl.lib.http_logger.service.RequestResult
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object HttpUtils {

    /**
     * POST请求
     */
    fun postJson(urlPath: String, jsonStr: String, headers: Map<String, String>?): RequestResult {
        try {
            // 准备请求Http
            val url = URL(urlPath)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            if (headers != null && headers.isNotEmpty()) {
                val iterator = headers.entries.iterator()
                while (iterator.hasNext()) {
                    val next = iterator.next()
                    conn.setRequestProperty(next.key, next.value)
                }
            }
            conn.doInput = true
            conn.doOutput = true

            conn.outputStream.write(jsonStr.toByteArray())
            conn.outputStream.flush()
            conn.outputStream.close()

            val reader = BufferedReader(InputStreamReader(conn.inputStream))
            val buffer = StringBuilder()
            var responseLine: String?
            while (reader.readLine().also { responseLine = it } != null) {
                buffer.append(responseLine!!.trim())
            }
            reader.close()
            conn.disconnect()
            return RequestResult(string = buffer.toString())
        } catch (ex: Exception) {
            return RequestResult(throwable = ex)
        }
    }
}