package com.musicgear.gas.instruments

import com.musicgear.gas.instruments.InstrumentsView.State
import com.musicgear.gas.instruments.InstrumentsView.StateChange
import com.musicgear.gas.instruments.databinding.FragmentInstrumentsBinding
import com.musicgear.gas.utils.basecomponents.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstrumentsFragment :
  BaseBindingFragment<FragmentInstrumentsBinding, State, StateChange, InstrumentsViewModel>(),
  InstrumentsView {

  override val viewModel: InstrumentsViewModel by viewModel()

  override fun layoutResId(): Int = R.layout.fragment_instruments

  override fun initIntents() {
  }

  override fun observeViewState() {
  }

  override fun render(state: State) {
  }
}