package com.emil.github.ui.myinfo

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emil.github.data.GithubLoginToken
import com.emil.github.data.ResultData
import com.emil.github.data.User
import com.emil.github.data.source.GithubRepository
import com.emil.github.network.LoadApiStatus
import com.emil.github.util.GithubLoginManager
import com.emil.github.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyInfoViewModel(private val githubRepository: GithubRepository
): ViewModel() {

    private var _githubCode: String = ""
    private var _userToken: GithubLoginToken? = null

    private val _myInfo = MutableLiveData<User>()
    val myInfo: LiveData<User>
        get() = _myInfo

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    fun getGithubCode(url: String) {
        Logger.w("webView get code\nurl = $url")
        val uri = Uri.parse(url)
        if (url.contains("code")) {
            _githubCode = uri.getQueryParameter("code") ?: ""
            Logger.w("code=$_githubCode")
            val tokenUrl = GithubLoginManager.getTokenUrl(_githubCode)
            getGithubToken(tokenUrl)
        }
    }

    private fun getGithubToken(url: String) {
        viewModelScope.launch {
            _status.value = LoadApiStatus.LOADING

            withContext(Dispatchers.IO) {
                githubRepository.getGithubToken(url)

            }.let { result ->
                Logger.d("userToken = $result")
                _userToken = when(result) {
                    is ResultData.Success -> {
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                        getMyInfo(result.data)
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

    private fun getMyInfo(userToken: GithubLoginToken) {
        viewModelScope.launch {
            _status.value = LoadApiStatus.LOADING

            withContext(Dispatchers.IO) {
                githubRepository.getMyInfo("${userToken.tokenType} ${userToken.accessToken}")
            }.let { result ->
                _myInfo.value = when(result) {
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


}