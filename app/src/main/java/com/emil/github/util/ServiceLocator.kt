package com.emil.github.util

import android.content.Context
import com.emil.github.data.source.DefaultGithubRepository
import com.emil.github.data.source.GithubDataSource
import com.emil.github.data.source.GithubRepository
import com.emil.github.data.source.remote.GithubRemoteDataSource

object ServiceLocator {

    @Volatile
    var githubRepository: GithubRepository? = null

    fun provideTaskRepository(context: Context): GithubRepository {
        synchronized(this) {
            return githubRepository ?: createGithubRepository(context)
        }
    }

    private fun createGithubRepository(context: Context): GithubRepository {
        return DefaultGithubRepository(GithubRemoteDataSource)
    }
}