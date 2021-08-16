package com.emil.github.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserListDataBean(
    val data: List<UserListData>? = null,
    val pagingUrl: String? = null
): Parcelable {
}