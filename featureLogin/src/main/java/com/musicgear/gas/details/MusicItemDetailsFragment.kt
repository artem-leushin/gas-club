package com.musicgear.gas.details

import com.musicgear.gas.details.MusicItemDetailsView.State
import com.musicgear.gas.details.MusicItemDetailsView.StateChange
import com.musicgear.gas.utils.basecomponents.BaseFragment

class MusicItemDetailsFragment : BaseFragment<State, StateChange, MusicItemDetailsViewModel>(),
  MusicItemDetailsView {
  override val viewModel: MusicItemDetailsViewModel
    get() = TODO("not implemented")

  override fun layoutResId(): Int {
    TODO("not implemented")
  }

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

