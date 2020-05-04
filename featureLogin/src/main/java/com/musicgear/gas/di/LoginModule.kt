package com.musicgear.gas.di

import com.musicgear.gas.login.LoginViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
  viewModel<LoginViewModel>()
}