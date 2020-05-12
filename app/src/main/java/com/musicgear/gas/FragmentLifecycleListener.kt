package com.musicgear.gas

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.musicgear.gas.categories.CategoriesFragment
import com.musicgear.gas.login.LoginFragment
import com.musicgear.gas.main.MainView.Intent
import com.musicgear.gas.start.StartFragment

class FragmentLifecycleListener(
  private val publishIntent: (Intent) -> Unit
) :
  FragmentManager.FragmentLifecycleCallbacks() {

  override fun onFragmentAttached(fm: FragmentManager, fragment: Fragment, context: Context) {
    when (fragment) {
      is LoginFragment,
      is StartFragment -> publishIntent(Intent.HideControls())
      is CategoriesFragment -> publishIntent(Intent.ShowControls())
    }
  }
}