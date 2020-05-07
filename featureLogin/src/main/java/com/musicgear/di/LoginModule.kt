package com.musicgear.di

import com.musicgear.gas.domain.interactor.LoginWithVkUseCase
import com.musicgear.gas.domain.interactor.ProceedLoginWithVkUseCase
import com.musicgear.gas.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
  viewModel { LoginViewModel(startLogin = get(), proceedLogin = get(), coordinator = get()) }
  factory { LoginWithVkUseCase(authService = get()) }
  factory { ProceedLoginWithVkUseCase(authService = get()) }
}