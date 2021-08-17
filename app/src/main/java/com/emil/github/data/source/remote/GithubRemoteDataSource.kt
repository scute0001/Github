package com.emil.github.data.source.remote

import com.emil.github.R
import com.emil.github.data.*
import com.emil.github.data.source.GithubDataSource
import com.emil.github.network.GithubApi
import com.emil.github.util.Logger
import com.emil.github.util.SystemUtilTool.getString
import com.emil.github.util.SystemUtilTool.isInternetConnected
import com.emil.github.util.SystemUtilTool.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GithubRemoteDataSource: GithubDataSource {
    override suspend fun getUsers(since: Int): ResultData<UserListDataBean> {

        if (!isInternetConnected()) {
            withContext(Dispatchers.Main) {
                showToast(getString(R.string.internet_is_not_connected))
            }
            return ResultData.Fail(getString(R.string.internet_is_not_connected))
        }

        return try {
            val response = GithubApi.retrofitService.getUsers(since)
            val header = response.headers()["link"]
            var url = ""

            header?.let {
                val fIndex = header.indexOf("<") + 1
                val eIndex = header.indexOf(">")
                url = header.substring(fIndex, eIndex)
            }
            val result = UserListDataBean(
                data = response.body(),
                pagingUrl = url
            )
            ResultData.Success(result)

        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

    override suspend fun getUserDetail(url: String): ResultData<User> {

        if (!isInternetConnected()) {
            withContext(Dispatchers.Main) {
                showToast(getString(R.string.internet_is_not_connected))
            }
            return ResultData.Fail(getString(R.string.internet_is_not_connected))
        }

        return try {
            val result = GithubApi.retrofitService.getUserDetail(url)
            ResultData.Success(result)

        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

    override suspend fun getMoreUser(url: String): ResultData<UserListDataBean> {

        if (!isInternetConnected()) {
            withContext(Dispatchers.Main) {
                showToast(getString(R.string.internet_is_not_connected))
            }
            return ResultData.Fail(getString(R.string.internet_is_not_connected))
        }

        return try {
            val response = GithubApi.retrofitService.getMoreUser(url)
            val header = response.headers()["link"]
            var nextUrl = ""

            header?.let {
                val fIndex = header.indexOf("<") + 1
                val eIndex = header.indexOf(">")
                nextUrl = header.substring(fIndex, eIndex)
            }
            val result = UserListDataBean(
                data = response.body(),
                pagingUrl = nextUrl
            )
            Logger.w("API gerMoreUser paging url=${result.pagingUrl}")
            ResultData.Success(result)

        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

    override suspend fun getGithubToken(url: String): ResultData<GithubLoginToken> {

        if (!isInternetConnected()) {
            withContext(Dispatchers.Main) {
                showToast(getString(R.string.internet_is_not_connected))
            }
            return ResultData.Fail(getString(R.string.internet_is_not_connected))
        }

        return try {
            val result = GithubApi.retrofitService.getGithubToken(url = url)
            Logger.d("token result = $result")
            ResultData.Success(result)
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

    override suspend fun getMyInfo(token: String): ResultData<User> {

        if (!isInternetConnected()) {
            withContext(Dispatchers.Main) {
                showToast(getString(R.string.internet_is_not_connected))
            }
            return ResultData.Fail(getString(R.string.internet_is_not_connected))
        }

        return try {
            val result = GithubApi.retrofitService.getMyInfo(token)
            Logger.d("MyInfo result = $result")
            ResultData.Success(result)
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }
}