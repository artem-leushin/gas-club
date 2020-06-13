package com.musicgear.gas.login.databinding

import androidx.databinding.DataBindingComponent
import com.musicgear.gas.utils.imageloading.bindingadapter.ImageBindingAdapter

class ImageBindingComponent(
  private val imageBindingAdapters: ImageBindingAdapter
) : DataBindingComponent {
  override fun getImageBindingAdapter(): ImageBindingAdapter = imageBindingAdapters
}