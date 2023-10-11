package ru.netology.nework.handler


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.netology.nework.R

fun ImageView.loadAvatar(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_loading_100dp)
        .error(R.drawable.ic_error_100dp)
        .transition(DrawableTransitionOptions.withCrossFade())
        .transform(CircleCrop())
        .timeout(10_000)
        .into(this)
}
fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_loading_100dp)
        .error(R.drawable.ic_error_100dp)
        .timeout(10_000)
        .into(this)
}

