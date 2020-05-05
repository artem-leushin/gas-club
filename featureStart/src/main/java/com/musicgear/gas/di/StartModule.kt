package com.musicgear.gas.di

import com.musicgear.gas.domain.interactor.CheckIfUserIsLoggedInUseCase
import org.koin.dsl.module

val startModule = module {
  factory { CheckIfUserIsLoggedInUseCase(sessionStatusRepo = get()) }
}