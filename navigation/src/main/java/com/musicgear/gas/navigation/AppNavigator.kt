package com.musicgear.gas.navigation

import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

class AppNavigator {
  private lateinit var navController: NavController

  fun setNavController(navController: NavController) {
    this.navController = navController
  }

  fun setupToolbar(toolbar: Toolbar) {
  }

  fun goFromStartToLogin(extras: Navigator.Extras) {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.fade_in)
      .setExitAnim(R.anim.fade_out)
      .setPopUpTo(R.id.startFragment, true)
      .build()

    navController.navigate(R.id.from_start_to_login, null, navOptions, extras)
  }

  fun goToCategories() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.fade_in)
      .setExitAnim(R.anim.fade_out)
      .setPopUpTo(R.id.nav_graph_main, true)
      .build()
    navController.navigate(R.id.to_categories, null, navOptions)
  }

  fun goFromCategoriesToInstruments() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.fade_in)
      .setExitAnim(R.anim.fade_out)
      .build()
    navController.navigate(R.id.from_categories_to_instruments, null, navOptions)
  }

  fun goFromInstrumentsToDetails() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.fade_in)
      .setExitAnim(R.anim.fade_out)
      .build()
    navController.navigate(R.id.from_instruments_to_item_details, null, navOptions)
  }

}

