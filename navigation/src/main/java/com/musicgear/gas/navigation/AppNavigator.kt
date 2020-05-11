package com.musicgear.gas.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.musicgear.gas.categories.CategoriesView.DisplayableCategory
import com.musicgear.gas.domain.constants.ARG_CATEGORY
import com.musicgear.gas.domain.constants.ARG_INSTRUMENT
import com.musicgear.gas.instruments.InstrumentsView.Displayable.DisplayableInstrument

class AppNavigator {
  private lateinit var navController: NavController

  fun setNavController(navController: NavController) {
    this.navController = navController
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

  fun goFromCategoriesToInstruments(category: DisplayableCategory) {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.fragment_open_enter)
      .setExitAnim(R.anim.fragment_close_exit)
      .build()
    val args = bundleOf(ARG_CATEGORY to category.id)
    navController.navigate(R.id.from_categories_to_instruments, args, navOptions)
  }

  fun goFromInstrumentsToDetails(instrument: DisplayableInstrument) {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.fade_in)
      .setExitAnim(R.anim.fade_out)
      .build()
    val args = bundleOf(ARG_INSTRUMENT to instrument.id)
    navController.navigate(R.id.from_instruments_to_item_details, args, navOptions)
  }

  fun logout() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.fragment_open_enter)
      .setExitAnim(R.anim.fragment_close_exit)
      .setPopUpTo(R.id.nav_graph_main, true)
      .build()
    navController.navigate(R.id.to_login, null, navOptions)
  }
}


