package com.musicgear.gas.utils.imageloading

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView

interface ImageLoader {
  fun loadImg(imageView: ImageView, imgUrl: String? = "", args: Args)
  fun loadImg(imageView: ImageView, imgUrl: String? = "", args: Args, clientListener: () -> Unit)
  fun loadImg(imageView: ImageView, uri: Uri, args: Args)
  fun loadAssetImg(imageView: ImageView, assetName: String, args: Args)
  fun loadBitmap(iv: ImageView, bitmap: Bitmap, centerCrop: Boolean)
  fun clear(iv: ImageView)

  data class Args(
    val transformCenterCrop: Boolean = false,
    val transformCircle: Boolean = false,
    val alphaGradient: Boolean = false,
    val roundedCornersRadiusDp: Int = 0
  )
}