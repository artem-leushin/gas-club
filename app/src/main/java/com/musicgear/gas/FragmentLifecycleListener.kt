package com.musicgear.gas

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.musicgear.gas.categories.CategoriesFragment
import com.musicgear.gas.main.MainView.Intent

class FragmentLifecycleListener(
  private val publishIntent: (Intent) -> Unit
) :
  FragmentManager.FragmentLifecycleCallbacks() {

  override fun onFragmentAttached(fm: FragmentManager, fragment: Fragment, context: Context) {
    when (fragment) {
      is CategoriesFragment -> publishIntent(Intent.ShowControls())
    }
  }
}