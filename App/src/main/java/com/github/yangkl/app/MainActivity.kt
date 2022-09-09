package com.github.yangkl.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.yangkl.lib.http_logger.HttpLogger

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}