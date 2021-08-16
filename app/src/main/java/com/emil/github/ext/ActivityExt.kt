package com.emil.github.ext

import android.app.Activity
import com.emil.github.GithubApplication
import com.emil.github.data.factory.ViewModelFactory


fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as GithubApplication).githubRepository
    return ViewModelFactory(repository)
}