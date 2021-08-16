package com.emil.github.data

sealed class ResultData<out R> {
    data class Success<out T>(val data: T): ResultData<T>()
    data class Fail(val error: String): ResultData<Nothing>()
    data class Error(val exception: Exception): ResultData<Nothing>()
    object Loading: ResultData<Nothing>()

    override fun toString(): String {
        return when(this) {
            is Success<*> -> "Success[resultData = $data]"
            is Fail -> "Fail[error = $error]"
            is Error -> "Error[exception = ${exception.message}]"
            Loading -> "Loading"
        }
    }
}

val ResultData<*>.succeeded
    get() = this is ResultData.Success && data != null

