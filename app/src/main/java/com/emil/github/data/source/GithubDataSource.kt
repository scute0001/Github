package com.emil.github.data.source

import com.emil.github.data.ResultData
import com.emil.github.data.User
import com.emil.github.data.UserListData
import com.emil.github.data.UserListDataBean

interface GithubDataSource {
    suspend fun getUsers(since: Int): ResultData<UserListDataBean>

    suspend fun getUserDetail(url: String): ResultData<User>

    suspend fun getMoreUser(url: String): ResultData<UserListDataBean>
}