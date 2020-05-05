package com.musicgear.gas.di

import com.musicgear.gas.login.LoginCoordinator
import com.musicgear.gas.navigation.AppNavigator
import com.musicgear.gas.navigation.LoginCoordinatorImpl
import com.musicgear.gas.navigation.StartCoordinatorImpl
import com.musicgear.gas.start.StartCoordinator
import org.koin.dsl.module

val navigationModule = module {
  single(createdAtStart = true) { AppNavigator() }
  single<StartCoordinator> { StartCoordinatorImpl(navigator = get()) }
  single<LoginCoordinator> { LoginCoordinatorImpl(navigator = get()) }
}