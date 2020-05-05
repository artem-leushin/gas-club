package com.musicgear.gas.start

import android.view.View

interface StartCoordinator {
  fun goToLogin(transitions: Pair<View, String>? = null)
  fun goToMusicGear()
}