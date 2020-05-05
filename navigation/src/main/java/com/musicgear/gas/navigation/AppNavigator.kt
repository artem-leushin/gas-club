package com.musicgear.gas.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

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

  fun goToMusicGear() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.fade_in)
      .setExitAnim(R.anim.fade_out)
      .build()
    navController.navigate(R.id.to_music_gear, null, navOptions)
  }

  fun goFromMusicGearToInstruments() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.fade_in)
      .setExitAnim(R.anim.fade_out)
      .build()
    navController.navigate(R.id.from_music_gear_to_instruments, null, navOptions)
  }

  fun goFromInstrumentsToDetails() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.fade_in)
      .setExitAnim(R.anim.fade_out)
      .build()
    navController.navigate(R.id.from_instruments_to_details, null, navOptions)
  }

}

