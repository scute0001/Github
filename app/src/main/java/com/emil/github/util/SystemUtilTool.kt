package com.emil.github.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.emil.github.GithubApplication

object SystemUtilTool {
    fun isInternetConnected(): Boolean {
        val connectManager = GithubApplication.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun getString(resourceId: Int): String {
        return GithubApplication.instance.getString(resourceId)
    }

    fun getDimen(resourceId: Int): Float{
        return GithubApplication.instance.resources.getDimension(resourceId)
    }

    fun showToast(content: String, isShowLong: Boolean = false) {
        if (isShowLong) {
            Toast.makeText(GithubApplication.instance.applicationContext, content, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(GithubApplication.instance.applicationContext, content, Toast.LENGTH_SHORT).show()
        }
    }
}