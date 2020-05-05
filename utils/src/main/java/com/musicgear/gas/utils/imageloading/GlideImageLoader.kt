package com.musicgear.gas.utils.imageloading

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.musicgear.gas.utils.imageloading.ImageLoader.*
import com.musicgear.gas.utils.imageloading.transformation.AlphaTransformation
import com.musicgear.gas.utils.px
import java.util.ArrayList

object GlideImageLoader : ImageLoader {
  override fun loadImg(imageView: ImageView, imgUrl: String?, args: Args) {
    require(!args.transformCircle || args.roundedCornersRadiusDp == 0) {
      "Cannot apply transformCircle and roundedCornersRadiusDp attrs at the same time"
    }

    val optionsTransformation = extractTransformOptions(args)

    GlideApp.with(imageView)
      .load(imgUrl)
      .apply(optionsTransformation)
      .into(imageView)
  }

  private fun extractTransformOptions(args: Args): RequestOptions {
    val transformationRequests = ArrayList<Transformation<Bitmap>>().apply {
      with(args) {
        if (transformCenterCrop) add(CenterCrop())
        if (transformCircle) add(CircleCrop())
        if (roundedCornersRadiusDp > 0) add(RoundedCorners(roundedCornersRadiusDp.px))
        if (alphaGradient) add(AlphaTransformation())
      }
    }

    return if (transformationRequests.isNotEmpty()) {
      RequestOptions.bitmapTransform(MultiTransformation(transformationRequests))
    } else RequestOptions.noTransformation()
  }

  override fun loadBitmap(iv: ImageView, bitmap: Bitmap, centerCrop: Boolean) {
    val options = RequestOptions().apply {
      if (centerCrop)
        centerCrop()
    }

    GlideApp.with(iv)
      .load(bitmap)
      .apply(options)
      .into(iv)
  }

  override fun clear(iv: ImageView) {
    GlideApp.with(iv)
      .clear(iv)
  }
}