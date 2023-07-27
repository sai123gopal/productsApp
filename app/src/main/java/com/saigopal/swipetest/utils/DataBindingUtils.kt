package com.saigopal.swipetest.utils

import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.saigopal.swipetest.R


object DataBindingUtils {

    @BindingAdapter("app:loadImage")
    @JvmStatic
    fun loadImage(view: ImageView, url:String?){
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.outline_image_24)
            .error(R.drawable.outline_image_24)
            .into(view)

        view.setOnClickListener{
            kotlin.run {
                if(url!!.isNotEmpty()) {
                    val bitmap = (view.drawable as BitmapDrawable).bitmap
                    ShowImage(view.context, bitmap)
                }
            }

        }
    }


}