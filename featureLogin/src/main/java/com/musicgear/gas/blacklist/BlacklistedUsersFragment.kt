package com.musicgear.gas.blacklist

import com.musicgear.gas.blacklist.BlacklistedUsersView.State
import com.musicgear.gas.blacklist.BlacklistedUsersView.StateChange
import com.musicgear.gas.login.R
import com.musicgear.gas.utils.basecomponents.BaseFragment

class BlacklistedUsersFragment : BaseFragment<State, StateChange, BlacklistedUsersViewModel>(R.layout.fragment_blacklist),
  BlacklistedUsersView {
  override val viewModel: BlacklistedUsersViewModel
    get() = TODO("not implemented")

  override fun initIntents() {
    TODO("not implemented")
  }

  override fun observeViewState() {
    TODO("not implemented")
  }

  override fun render(state: State) {
    TODO("not implemented")
  }
}

