package com.emil.github.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val login: String = "",
    @Json(name = "avatar_url") val avatarUrl: String = "",
    val id: Int = -1,
    val location: String? = "",
    val url: String = "",
    val name: String? = "",
    val blog: String? = "",
    val followers: Int? = 0,
    val following: Int? = 0
): Parcelable {
}

//    "login": "evanphx",
//    "id": 7,
//    "node_id": "MDQ6VXNlcjc=",
//    "avatar_url": "https://avatars.githubusercontent.com/u/7?v=4",
//    "gravatar_id": "",
//    "url": "https://api.github.com/users/evanphx",
//    "html_url": "https://github.com/evanphx",
//    "followers_url": "https://api.github.com/users/evanphx/followers",
//    "following_url": "https://api.github.com/users/evanphx/following{/other_user}",
//    "gists_url": "https://api.github.com/users/evanphx/gists{/gist_id}",
//    "starred_url": "https://api.github.com/users/evanphx/starred{/owner}{/repo}",
//    "subscriptions_url": "https://api.github.com/users/evanphx/subscriptions",
//    "organizations_url": "https://api.github.com/users/evanphx/orgs",
//    "repos_url": "https://api.github.com/users/evanphx/repos",
//    "events_url": "https://api.github.com/users/evanphx/events{/privacy}",
//    "received_events_url": "https://api.github.com/users/evanphx/received_events",
//    "type": "User",
//    "site_admin": false,
//    "name": "Evan Phoenix",
//    "company": "@hashicorp ",
//    "blog": "http://blog.fallingsnow.net",
//    "location": "Los Angeles, CA",
//    "email": null,
//    "hireable": null,
//    "bio": null,
//    "twitter_username": null,
//    "public_repos": 166,
//    "public_gists": 445,
//    "followers": 1397,
//    "following": 7,
//    "created_at": "2008-01-12T16:46:24Z",
//    "updated_at": "2021-08-06T00:15:50Z"

