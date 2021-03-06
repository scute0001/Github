package com.emil.github.data.source

import com.emil.github.data.*

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

    override suspend fun getGithubToken(url: String): ResultData<GithubLoginToken> {
        return githubRemoteDataSource.getGithubToken(url)
    }

    override suspend fun getMyInfo(token: String): ResultData<User> {
        return githubRemoteDataSource.getMyInfo(token)
    }
}