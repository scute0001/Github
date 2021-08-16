package com.emil.github.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emil.github.MainActivityViewModel
import com.emil.github.data.source.GithubRepository
import com.emil.github.ui.users.UsersViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory constructor(
    private val githubRepository: GithubRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainActivityViewModel::class.java) ->
                    MainActivityViewModel()
                isAssignableFrom(UsersViewModel::class.java) ->
                    UsersViewModel(githubRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}