package com.emil.github.data.source

import com.emil.github.data.*

interface GithubRepository {
    suspend fun getUsers(since: Int): ResultData<UserListDataBean>

    suspend fun getUserDetail(url: String): ResultData<User>

    suspend fun getMoreUser(url: String): ResultData<UserListDataBean>

    suspend fun getGithubToken(url: String): ResultData<GithubLoginToken>

    suspend fun getMyInfo(token: String): ResultData<User>
}