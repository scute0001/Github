package com.emil.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emil.github.data.source.GithubRepository
import com.emil.github.util.PageInfo

class MainActivityViewModel(): ViewModel() {
    private val _currentPage = MutableLiveData<PageInfo>()
    val currentPage: LiveData<PageInfo>
        get() = _currentPage

    private val _isPageChange = MutableLiveData<Boolean>()
    val isPageChange: LiveData<Boolean>
        get() = _isPageChange

    init {
        _currentPage.value = PageInfo.USERS
    }

    fun setCurrentPage(currentPage: PageInfo) {
        _currentPage.value = currentPage
    }

    fun setPageChange() {
        _isPageChange.value = true
    }

    fun pageChangeDone() {
        _isPageChange.value = false
    }
}