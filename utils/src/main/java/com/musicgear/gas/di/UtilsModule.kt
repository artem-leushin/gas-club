package com.musicgear.gas.di

import com.musicgear.gas.utils.imageloading.GlideImageLoader
import com.musicgear.gas.utils.imageloading.ImageLoader
import com.musicgear.gas.utils.imageloading.bindingadapter.ImageBindingAdapter
import org.koin.dsl.module

val utilsModule = module {
  single<ImageLoader> { GlideImageLoader }
  single { ImageBindingAdapter(imageLoader = get()) }
}
