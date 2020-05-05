package com.musicgear.gas.utils.imageloading

import android.graphics.Bitmap
import android.widget.ImageView

interface ImageLoader {
  fun loadImg(imageView: ImageView, imgUrl: String? = "", args: Args)
  fun loadBitmap(iv: ImageView, bitmap: Bitmap, centerCrop: Boolean)
  fun clear(iv: ImageView)

  data class Args(
    val transformCenterCrop: Boolean = false,
    val transformCircle: Boolean = false,
    val alphaGradient: Boolean = false,
    val roundedCornersRadiusDp: Int = 0
  )
}

