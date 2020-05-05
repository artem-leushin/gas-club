package com.musicgear.gas.utils.imageloading.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.musicgear.gas.utils.imageloading.ImageLoader

class ImageBindingAdapter(private val imageLoader: ImageLoader) {

  lateinit var packageName: String

  @BindingAdapter(
    value = [
      "imgUrl",
      "alphaGradient",
      "transformCenterCrop",
      "transformCircle",
      "transformRoundedCorners"],
    requireAll = false
  )
  fun loadImage(
    imageView: ImageView,
    imgUrl: String? = null,
    alphaGradient: Boolean = false,
    transformCenterCrop: Boolean = false,
    transformCircle: Boolean = false,
    transformRoundedCorners: Int = 0
  ) {
    if (!::packageName.isInitialized)
      packageName = imageView.context.packageName

    imageLoader.loadImg(
      imageView,
      imgUrl,
      ImageLoader.Args(
        transformCenterCrop = transformCenterCrop,
        transformCircle = transformCircle,
        alphaGradient = alphaGradient,
        roundedCornersRadiusDp = transformRoundedCorners
      )
    )
  }

  @BindingAdapter(
    value = [
      "imgResId",
      "alphaGradient",
      "transformCenterCrop",
      "transformCircle",
      "transformRoundedCorners"],
    requireAll = false
  )
  fun loadImage(
    imageView: ImageView,
    imgResId: Int? = 0,
    alphaGradient: Boolean = false,
    transformCenterCrop: Boolean = false,
    transformCircle: Boolean = false,
    transformRoundedCorners: Int = 0
  ) {
    if (!::packageName.isInitialized)
      packageName = imageView.context.packageName

    val imageUrl = "android.resource://$packageName/$imgResId"

    imageLoader.loadImg(
      imageView,
      imageUrl,
      ImageLoader.Args(
        transformCenterCrop = transformCenterCrop,
        transformCircle = transformCircle,
        alphaGradient = alphaGradient,
        roundedCornersRadiusDp = transformRoundedCorners
      )
    )
  }

  @BindingAdapter(
    value = [
      "assetName",
      "alphaGradient",
      "transformCenterCrop",
      "transformCircle",
      "transformRoundedCorners"],
    requireAll = false
  )
  fun loadAssetImage(
    imageView: ImageView,
    assetName: String = "",
    alphaGradient: Boolean = false,
    transformCenterCrop: Boolean = false,
    transformCircle: Boolean = false,
    transformRoundedCorners: Int = 0
  ) {
    if (!::packageName.isInitialized)
      packageName = imageView.context.packageName

    imageLoader.loadAssetImg(
      imageView,
      assetName,
      ImageLoader.Args(
        transformCenterCrop = transformCenterCrop,
        transformCircle = transformCircle,
        alphaGradient = alphaGradient,
        roundedCornersRadiusDp = transformRoundedCorners
      )
    )
  }

}