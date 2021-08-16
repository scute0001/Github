package com.emil.github

import android.app.Application
import com.emil.github.data.source.GithubRepository
import com.emil.github.util.ServiceLocator
import kotlin.properties.Delegates


class GithubApplication: Application() {
    val githubRepository: GithubRepository
        get() = ServiceLocator.provideTaskRepository(this)

    companion object {
        var instance: GithubApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}