package com.emil.github.ui.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emil.github.data.ResultData
import com.emil.github.data.User
import com.emil.github.data.UserListData
import com.emil.github.data.source.GithubRepository
import com.emil.github.network.LoadApiStatus
import com.emil.github.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel(
    private val githubRepository: GithubRepository
): ViewModel() {

    private val _usersList = MutableLiveData<List<UserListData>>()
    val userList: LiveData<List<UserListData>>
        get() = _usersList

    private val _sincePage = MutableLiveData<Int>()
    val sincePage: LiveData<Int>
        get() = _sincePage

    private val _userDetail = MutableLiveData<User>()
    val userDetail: LiveData<User>
        get() = _userDetail

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private var pagingUrl: String = ""

    init {
        _sincePage.value = 0
        getUsers(sincePage.value ?: 0)
    }

    fun navToDetailDone() {
        _userDetail.value = null
    }

    fun getUserDetail(url: String) {
        viewModelScope.launch {
            _status.value = LoadApiStatus.LOADING

            withContext(Dispatchers.IO) {
                githubRepository.getUserDetail(url)

            }.let { result ->
                Logger.d("userDetail = $result")
                _userDetail.value = when(result) {
                    is ResultData.Success -> {
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                        result.data
                    }
                    is ResultData.Fail -> {
                        _error.value = result.error
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                    is ResultData.Error -> {
                        _error.value = result.exception.toString()
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                    else -> {
                        _error.value = "Unknown Error"
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                }
            }
        }
    }

    fun getMoreUsers() {

        viewModelScope.launch {
            _status.value = LoadApiStatus.LOADING

            withContext(Dispatchers.IO) {
                if (pagingUrl.isNotEmpty()) {
                    githubRepository.getMoreUser(pagingUrl)
                } else {
                    githubRepository.getUsers(sincePage.value ?: 0)
                }

            }.let { result ->
                Logger.d("get more = $result")
                _usersList.value = when(result) {
                    is ResultData.Success -> {
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                        result.data.pagingUrl?.let {
                            pagingUrl = it
                        }
                        val newList: MutableList<UserListData> = userList.value?.toMutableList() ?: mutableListOf()
                        result.data.data?.let { newList.addAll(it) }
                        newList
                    }
                    is ResultData.Fail -> {
                        _error.value = result.error
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                    is ResultData.Error -> {
                        _error.value = result.exception.toString()
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                    else -> {
                        _error.value = "Unknown Error"
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                }
            }
        }

    }

    private fun getUsers(since: Int) {
        viewModelScope.launch {
            _status.value = LoadApiStatus.LOADING

            withContext(Dispatchers.IO) {
                githubRepository.getUsers(since)

            }.let { result ->
                Logger.d("getUsers = $result")
                _usersList.value = when(result) {
                    is ResultData.Success -> {
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                        result.data.pagingUrl?.let {
                            pagingUrl = it
                        }
                        result.data.data
                    }
                    is ResultData.Fail -> {
                        _error.value = result.error
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                    is ResultData.Error -> {
                        _error.value = result.exception.toString()
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                    else -> {
                        _error.value = "Unknown Error"
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                }
            }
        }
    }



}