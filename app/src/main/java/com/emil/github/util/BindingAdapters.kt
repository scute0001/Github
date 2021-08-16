package com.emil.github.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.emil.github.GithubApplication
import com.emil.github.R

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUrl = it.toUri().buildUpon().build()
        Glide.with(imgView.context)
            .load(imgUrl)
            .placeholder(R.drawable.icon_user_placeholder_24)
            .error(R.drawable.icon_user_placeholder_24)
            .transform(CenterCrop(), RoundedCorners(SystemUtilTool.getDimen(R.dimen.size_pic).toInt()))
            .into(imgView)
    }
}

@BindingAdapter("imageDetailUrl")
fun bindDetailImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUrl = it.toUri().buildUpon().build()
        Glide.with(imgView.context)
            .load(imgUrl)
            .placeholder(R.drawable.icon_user_placeholder_24)
            .error(R.drawable.icon_user_placeholder_24)
            .transform(CenterCrop(), RoundedCorners(SystemUtilTool.getDimen(R.dimen.size_pic_large).toInt()))
            .into(imgView)
    }
}

@BindingAdapter("fullNameSplit")
fun bindName(textView: TextView, fullName: String?) {
    if (fullName != null) {
        val name = fullName.split(" ")
        textView.text = name[0]
    } else {
        textView.text = GithubApplication.instance.getString(R.string.no_data)
    }
}

@BindingAdapter("setString")
fun bindString(textView: TextView, string: String?) {
    if (string != null && string != "") {
        textView.text = string
    } else {
        textView.text = GithubApplication.instance.getString(R.string.no_data)
    }
}