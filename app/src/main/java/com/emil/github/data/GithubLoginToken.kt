package com.emil.github.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubLoginToken(
    @Json(name = "access_token") val accessToken: String = "",
    @Json(name = "token_type") val tokenType: String = "",
    val scope: String = ""
): Parcelable {
}

//@Parcelize
//data class GithubLoginToken(
//    val access_token: String = ""
//): Parcelable {
//}