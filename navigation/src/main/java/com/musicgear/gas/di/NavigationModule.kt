package com.musicgear.gas.di

import com.musicgear.gas.categories.CategoriesCoordinator
import com.musicgear.gas.instruments.InstrumentsCoordinator
import com.musicgear.gas.login.LoginCoordinator
import com.musicgear.gas.navigation.AppNavigator
import com.musicgear.gas.navigation.CategoriesCoordinatorImpl
import com.musicgear.gas.navigation.InstrumentsCoordinatorImpl
import com.musicgear.gas.navigation.LoginCoordinatorImpl
import com.musicgear.gas.navigation.MainCoordinator
import com.musicgear.gas.navigation.MainCoordinatorImpl
import com.musicgear.gas.navigation.StartCoordinatorImpl
import com.musicgear.gas.start.StartCoordinator
import org.koin.dsl.module

val navigationModule = module {
  single(createdAtStart = true) { AppNavigator() }
  single<StartCoordinator> { StartCoordinatorImpl(navigator = get()) }
  single<LoginCoordinator> { LoginCoordinatorImpl(navigator = get()) }
  single<CategoriesCoordinator> { CategoriesCoordinatorImpl(navigator = get()) }
  single<InstrumentsCoordinator> { InstrumentsCoordinatorImpl(navigator = get()) }
  single<MainCoordinator> { MainCoordinatorImpl(navigator = get()) }
}