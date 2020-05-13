package com.musicgear.gas.details

import com.musicgear.gas.details.DetailsView.State
import com.musicgear.gas.details.DetailsView.StateChange
import com.musicgear.gas.details.databinding.FragmentDetailsBinding
import com.musicgear.gas.utils.basecomponents.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment :
  BaseBindingFragment<FragmentDetailsBinding, State, StateChange, DetailsViewModel>(),
  DetailsView {

  override val viewModel: DetailsViewModel by viewModel()

  override fun layoutResId(): Int = R.layout.fragment_details

  override fun initIntents() {
  }

  override fun render(state: State) {
  }
}

