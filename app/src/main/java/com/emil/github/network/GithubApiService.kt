package com.emil.github.network

import com.emil.github.BuildConfig
import com.emil.github.data.User
import com.emil.github.data.UserListData
import com.emil.github.data.UserListDataBean
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

private const val HOST_NAME = "api.github.com"
private const val BASE_URL = "https://$HOST_NAME/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface  GithubApiService {

//    https://api.github.com/users?since=0
    @GET(value = "users")
    suspend fun getUsers(@Query(value = "since") sinceId: Int = 0): Response<List<UserListData>>

    @GET
    suspend fun getUserDetail(@Url url: String): User

    @GET
    suspend fun getMoreUser(@Url url: String): Response<List<UserListData>>

}

object GithubApi {
    val retrofitService: GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }
}