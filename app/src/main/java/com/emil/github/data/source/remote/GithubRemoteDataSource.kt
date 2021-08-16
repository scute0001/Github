package com.emil.github.data.source.remote

import android.util.Log
import com.emil.github.data.ResultData
import com.emil.github.data.User
import com.emil.github.data.UserListData
import com.emil.github.data.UserListDataBean
import com.emil.github.data.source.GithubDataSource
import com.emil.github.network.GithubApi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object GithubRemoteDataSource: GithubDataSource {
    override suspend fun getUsers(since: Int): ResultData<UserListDataBean> {
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
        return try {
            val result = GithubApi.retrofitService.getUserDetail(url)
            ResultData.Success(result)

        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

    override suspend fun getMoreUser(url: String): ResultData<UserListDataBean> {
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
                pagingUrl = url
            )
            ResultData.Success(result)

        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }
}