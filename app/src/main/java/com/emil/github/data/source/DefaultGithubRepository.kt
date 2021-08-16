package com.emil.github.data.source

import com.emil.github.data.ResultData
import com.emil.github.data.User
import com.emil.github.data.UserListData
import com.emil.github.data.UserListDataBean

class DefaultGithubRepository(private val githubRemoteDataSource: GithubDataSource
): GithubRepository {
    override suspend fun getUsers(since: Int): ResultData<UserListDataBean> {
        return githubRemoteDataSource.getUsers(since)
    }

    override suspend fun getUserDetail(url: String): ResultData<User> {
        return githubRemoteDataSource.getUserDetail(url)
    }

    override suspend fun getMoreUser(url: String): ResultData<UserListDataBean> {
        return githubRemoteDataSource.getMoreUser(url)
    }
}