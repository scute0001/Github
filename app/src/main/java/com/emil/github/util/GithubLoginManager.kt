package com.emil.github.util

import java.util.concurrent.TimeUnit

object GithubLoginManager {
    const val CLIENT_ID = "6005c4ef02afa1903fb5"
    const val CLIENT_SECRET = "b6b5ec9983cc4c4343a3c2d8336cacfc89b8236c"
    const val SCOPE = "read:user,user:email"
    const val AUTH_URL = "https://github.com/login/oauth/authorize"
    const val TOKEN_URL = "https://github.com/login/oauth/access_token"

    fun getTokenUrl(code: String): String {
        return TOKEN_URL + "?code=" + code + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET
    }

    fun getCodeUrl(): String {
        return AUTH_URL + "?client_id=" + CLIENT_ID + "&scope=" + SCOPE + "&state=" +
                TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
    }
}