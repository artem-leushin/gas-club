package com.musicgear.gas.di

import androidx.databinding.DataBindingComponent
import com.musicgear.gas.utils.imageloading.GlideImageLoader
import com.musicgear.gas.utils.imageloading.ImageLoader
import com.musicgear.gas.utils.imageloading.bindingadapter.ImageBindingAdapter
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.dsl.module

val utilsModule = module {
  single<ImageLoader> { GlideImageLoader }
  single { ImageBindingAdapter(imageLoader = get()) }
}

class BindingComponent : DataBindingComponent, KoinComponent {

  private val imageBindingAdapters: ImageBindingAdapter by inject()
  override fun getImageBindingAdapter(): ImageBindingAdapter = imageBindingAdapters
}