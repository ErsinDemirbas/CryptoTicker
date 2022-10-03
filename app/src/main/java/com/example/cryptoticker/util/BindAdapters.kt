package com.example.cryptoticker.util

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cryptoticker.R

@BindingAdapter("android:setValue")
fun setValue(textView : TextView, value : Double){
    textView.text = value.toString()
}

fun ImageView.downloadPicture(url : String?, placeholder: CircularProgressDrawable){
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)

    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun doPlaceHolder(context : Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:setImageResource")
fun setImageResource(view : ImageView , url: String?){
    view.downloadPicture(url, doPlaceHolder(view.context))
}


