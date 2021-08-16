package com.emil.github.util

import android.os.Build
import android.util.Log
import com.emil.github.BuildConfig
import java.nio.file.attribute.AclEntry

object Logger {
    private const val TAG = "Emil-Github-login-example"

    fun v(content: String) { if (BuildConfig.LOGGER_VISIABLE) Log.v(TAG, content)}
    fun d(content: String) { if (BuildConfig.LOGGER_VISIABLE) Log.d(TAG, content)}
    fun i(content: String) { if (BuildConfig.LOGGER_VISIABLE) Log.i(TAG, content)}
    fun w(content: String) { if (BuildConfig.LOGGER_VISIABLE) Log.w(TAG, content)}
    fun e(content: String) { if (BuildConfig.LOGGER_VISIABLE) Log.e(TAG, content)}
}