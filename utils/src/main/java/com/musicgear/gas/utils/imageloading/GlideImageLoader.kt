package com.musicgear.gas.utils.imageloading

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.musicgear.gas.utils.R
import com.musicgear.gas.utils.imageloading.ImageLoader.Args
import com.musicgear.gas.utils.imageloading.transformation.AlphaGradientTransformation
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
      .placeholder(R.drawable.ic_placeholder)
      .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
      .apply(optionsTransformation)
      .into(imageView)
  }

  override fun loadImg(
    imageView: ImageView,
    imgUrl: String?,
    args: Args,
    clientListener: () -> Unit
  ) {
    val optionsTransformation = extractTransformOptions(args)

    GlideApp.with(imageView)
      .load(imgUrl)
      .placeholder(R.drawable.ic_placeholder)
      .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
      .listener(requestListener(clientListener))
      .apply(optionsTransformation)
      .into(imageView)
  }

  override fun loadImg(imageView: ImageView, uri: Uri, args: Args) {
    val optionsTransformation = extractTransformOptions(args)

    GlideApp.with(imageView)
      .load(uri)
      .placeholder(R.drawable.ic_placeholder)
      .apply(optionsTransformation)
      .into(imageView)
  }

  override fun loadAssetImg(imageView: ImageView, assetName: String, args: Args) {
    val optionsTransformation = extractTransformOptions(args)
    val uri = Uri.parse("file:///android_asset/$assetName")

    GlideApp.with(imageView)
      .load(uri)
      .apply(optionsTransformation)
      .into(imageView)
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

  private fun extractTransformOptions(args: Args): RequestOptions {
    val transformationRequests = ArrayList<Transformation<Bitmap>>().apply {
      with(args) {
        if (transformCenterCrop) add(CenterCrop())
        if (transformCircle) add(CircleCrop())
        if (roundedCornersRadiusDp > 0) add(RoundedCorners(roundedCornersRadiusDp.px))
        if (alphaGradient) add(AlphaGradientTransformation())
      }
    }

    return if (transformationRequests.isNotEmpty()) {
      RequestOptions.bitmapTransform(MultiTransformation(transformationRequests))
    } else RequestOptions.noTransformation()
  }

  private fun requestListener(clientListener: () -> Unit) =
    object : RequestListener<Drawable> {
      override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
      ): Boolean {
        clientListener()
        return false
      }

      override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
      ): Boolean {
        clientListener()
        return false
      }
    }
}