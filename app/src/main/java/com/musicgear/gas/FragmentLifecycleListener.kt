package com.musicgear.gas

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.musicgear.gas.categories.CategoriesFragment
import com.musicgear.gas.instruments.details.DetailsFragment
import com.musicgear.gas.instruments.master.InstrumentsFragment
import com.musicgear.gas.login.LoginFragment
import com.musicgear.gas.main.MainView.Intent
import com.musicgear.gas.main.MainView.Intent.HideControls
import com.musicgear.gas.main.MainView.Intent.ShowControls
import com.musicgear.gas.main.MainView.Intent.SwitchToMode
import com.musicgear.gas.main.MainView.StateChange.BottomAppBarMode.None
import com.musicgear.gas.main.MainView.StateChange.BottomAppBarMode.PostPhoto
import com.musicgear.gas.main.MainView.StateChange.BottomAppBarMode.TalkToSeller
import com.musicgear.gas.start.StartFragment

class FragmentLifecycleListener(
  private val publishIntent: (Intent) -> Unit
) : FragmentManager.FragmentLifecycleCallbacks() {

  override fun onFragmentAttached(fm: FragmentManager, fragment: Fragment, context: Context) {
    when (fragment) {
      is LoginFragment,
      is StartFragment -> publishIntent(HideControls)
      is CategoriesFragment -> publishIntent(ShowControls)
    }
  }

  override fun onFragmentViewCreated(
    fm: FragmentManager,
    fragment: Fragment,
    v: View,
    savedInstanceState: Bundle?
  ) {
    when (fragment) {
      is InstrumentsFragment -> publishIntent(SwitchToMode(PostPhoto))
      is DetailsFragment -> publishIntent(SwitchToMode(TalkToSeller))
      is CategoriesFragment -> publishIntent(SwitchToMode(None))
    }
  }
}