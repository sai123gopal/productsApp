package com.saigopal.swipetest.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler
import com.saigopal.swipetest.R
import com.saigopal.swipetest.databinding.ImageDialogBinding


class ShowImage @SuppressLint("ClickableViewAccessibility") constructor(
    var context: Context,
    var bitmap: Bitmap) {

    init {
        val binding: ImageDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),R.layout.image_dialog,null,false)

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        binding.close.setOnClickListener { dialog.dismiss() }
        binding.image.setImageBitmap(bitmap)
        binding.image.setOnTouchListener(ImageMatrixTouchHandler(context))
        dialog.show()
    }

}