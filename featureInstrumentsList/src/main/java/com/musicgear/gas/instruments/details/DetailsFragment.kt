package com.musicgear.gas.instruments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.musicgear.gas.domain.constants.ARG_INSTRUMENT
import com.musicgear.gas.instruments.R
import com.musicgear.gas.instruments.databinding.FragmentDetailsBinding
import com.musicgear.gas.instruments.details.DetailsView.Intent.LoadDetails
import com.musicgear.gas.instruments.master.InstrumentsView.Displayable.DisplayableInstrument
import com.musicgear.gas.utils.basecomponents.BaseBindingFragment
import com.musicgear.gas.utils.imageloading.ImageLoader
import com.musicgear.gas.utils.snackBarShort
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment :
  BaseBindingFragment<FragmentDetailsBinding, DetailsView.State, DetailsView.StateChange, DetailsViewModel>(),
  DetailsView {

  private val instrument: DisplayableInstrument by lazy {
    requireArguments().getParcelable<DisplayableInstrument>(ARG_INSTRUMENT)!!
  }

  override val viewModel: DetailsViewModel by viewModel()
  private val imageLoader: ImageLoader by inject()

  override fun layoutResId(): Int = R.layout.fragment_details

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    return viewBinding!!.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewBinding!!.ivPhoto.transitionName = instrument.transitionName

    imageLoader.loadImg(
      viewBinding!!.ivPhoto,
      instrument.photoUrl,
      ImageLoader.Args(transformCenterCrop = true)
    )
  }

  override fun initIntents() {
    viewModel.viewIntentsConsumer()
    viewModel.publishViewIntent(LoadDetails(instrument))
  }

  override fun render(state: DetailsView.State) {
    viewBinding!!.loading = state.loading

    if (state.success)
      viewBinding!!.details = state.details

    state.error?.message?.let { snackBarShort(it)?.show() }
  }
}

