package com.musicgear.gas.navigation

import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.musicgear.gas.categories.CategoriesCoordinator
import com.musicgear.gas.instruments.InstrumentsCoordinator
import com.musicgear.gas.login.LoginCoordinator
import com.musicgear.gas.start.StartCoordinator

class StartCoordinatorImpl(private val navigator: AppNavigator) : StartCoordinator {
  override fun goToLogin(transitions: Pair<View, String>?) {
    val extras = transitions?.let { FragmentNavigatorExtras(it) } ?: FragmentNavigatorExtras()
    navigator.goFromStartToLogin(extras)
  }

  override fun goToCategories() = navigator.goToCategories()
}

class LoginCoordinatorImpl(private val navigator: AppNavigator) : LoginCoordinator {
  override fun goToMusicGear() = navigator.goToCategories()
}

class CategoriesCoordinatorImpl(private val navigator: AppNavigator) : CategoriesCoordinator {
  override fun goToInstruments() = navigator.goFromCategoriesToInstruments()
}

class InstrumentsCoordinatorImpl(private val navigator: AppNavigator) : InstrumentsCoordinator {
  override fun goToItemDetails() = navigator.goFromInstrumentsToDetails()
}