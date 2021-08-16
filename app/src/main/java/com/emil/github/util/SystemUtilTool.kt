package com.emil.github.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
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
}