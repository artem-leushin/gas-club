package com.musicgear.gas.di

import androidx.databinding.DataBindingComponent
import com.musicgear.gas.utils.imageloading.bindingadapter.ImageBindingAdapter
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.dsl.module

val mainModule = module {
}


class BindingComponent : DataBindingComponent, KoinComponent {

  private val imgLoader: ImageBindingAdapter by inject()
  override fun getImageBindingAdapter(): ImageBindingAdapter = imgLoader
}
