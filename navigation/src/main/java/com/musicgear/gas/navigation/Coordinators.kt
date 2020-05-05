package com.musicgear.gas.navigation

import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.musicgear.gas.login.LoginCoordinator
import com.musicgear.gas.start.StartCoordinator

class StartCoordinatorImpl(private val navigator: AppNavigator) : StartCoordinator {
  override fun goToLogin(transitions: Pair<View, String>?) {
    val extras = transitions?.let { FragmentNavigatorExtras(it) } ?: FragmentNavigatorExtras()
    navigator.goFromStartToLogin(extras)
  }

  override fun goToMusicGear() = navigator.goToMusicGear()
}

class LoginCoordinatorImpl(private val navigator: AppNavigator) : LoginCoordinator {
  override fun goToMusicGear() = navigator.goToMusicGear()
}