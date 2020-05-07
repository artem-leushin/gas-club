package com.musicgear.gas.di

import android.content.res.Resources
import androidx.databinding.DataBindingComponent
import com.musicgear.gas.main.MainViewModel
import com.musicgear.gas.utils.imageloading.bindingadapter.ImageBindingAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.dsl.module

val mainModule = module {
  single<Resources> { androidContext().resources }
  viewModel { MainViewModel() }
}


class BindingComponent : DataBindingComponent, KoinComponent {

  private val imgLoader: ImageBindingAdapter by inject()
  override fun getImageBindingAdapter(): ImageBindingAdapter = imgLoader
}
