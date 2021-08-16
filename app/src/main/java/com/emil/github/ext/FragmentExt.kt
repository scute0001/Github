package com.emil.github.ext

import androidx.fragment.app.Fragment
import com.emil.github.GithubApplication
import com.emil.github.data.factory.ViewModelFactory

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as GithubApplication).githubRepository
    return ViewModelFactory(repository)
}