package com.emil.github.network

import com.emil.github.data.User
import com.emil.github.data.UserListData
import com.emil.github.data.GithubLoginToken
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val HOST_NAME = "api.github.com"
private const val BASE_URL = "https://$HOST_NAME/"

//https://github.com/login/oauth/access_token

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

    @POST
    suspend fun getGithubToken(@Header("Accept") type: String = "application/json", @Url url: String): GithubLoginToken
//    https://github.com/login/oauth/access_token?client_id=6005c4ef02afa1903fb5&client_secret=b6b5ec9983cc4c4343a3c2d8336cacfc89b8236c&code=53c2493cf753bceaf3b5

    @GET("user")
    suspend fun getMyInfo(@Header("Authorization") token: String): User

}

object GithubApi {
    val retrofitService: GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }
}