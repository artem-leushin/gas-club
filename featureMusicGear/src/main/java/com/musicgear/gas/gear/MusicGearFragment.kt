package com.musicgear.gas.gear

import com.musicgear.gas.gear.MusicGearView.*
import com.musicgear.gas.gear.databinding.FragmentMusicGearBinding
import com.musicgear.gas.utils.basecomponents.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MusicGearFragment :
  BaseBindingFragment<FragmentMusicGearBinding, State, StateChange, MusicGearViewModel>(),
  MusicGearView {

  override val viewModel: MusicGearViewModel by viewModel()

  override fun layoutResId(): Int = R.layout.fragment_music_gear

  override fun initIntents() {
  }

  override fun observeViewState() {
  }

  override fun render(state: State) {
  }
}