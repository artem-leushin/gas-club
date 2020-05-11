package com.musicgear.gas.instruments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import com.musicgear.gas.domain.constants.ARG_CATEGORY
import com.musicgear.gas.instruments.InstrumentsView.Intent.LoadNextPage
import com.musicgear.gas.instruments.InstrumentsView.Intent.RefreshInstruments
import com.musicgear.gas.instruments.InstrumentsView.State
import com.musicgear.gas.instruments.InstrumentsView.StateChange
import com.musicgear.gas.instruments.adapter.InstrumentsAdapter
import com.musicgear.gas.instruments.databinding.FragmentInstrumentsBinding
import com.musicgear.gas.utils.adapter.EndlessScrollListener
import com.musicgear.gas.utils.adapter.MarginItemDecoration
import com.musicgear.gas.utils.basecomponents.BaseBindingFragment
import com.musicgear.gas.utils.imageloading.ImageLoader
import com.musicgear.gas.utils.px
import com.musicgear.gas.utils.rx.plusAssign
import com.musicgear.gas.utils.snackBarShort
import kotlinx.android.synthetic.main.fragment_instruments.refresh_layout
import kotlinx.android.synthetic.main.fragment_instruments.rv_instruments
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstrumentsFragment :
  BaseBindingFragment<FragmentInstrumentsBinding, State, StateChange, InstrumentsViewModel>(),
  InstrumentsView {

  private val imageLoader: ImageLoader by inject()
  private lateinit var listAdapter: InstrumentsAdapter
  override val viewModel: InstrumentsViewModel by viewModel()

  override fun layoutResId(): Int = R.layout.fragment_instruments

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.categoryId = requireArguments().getInt(ARG_CATEGORY, 0)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initRecyclerView()
  }

  override fun initIntents() {
    viewSubscriptions += refresh_layout.refreshes()
      .map { RefreshInstruments }
      .subscribe(viewModel.viewIntentsConsumer())
  }

  private fun initRecyclerView() {
    this.listAdapter = InstrumentsAdapter(viewModel::publishViewIntent, imageLoader)

    rv_instruments.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = listAdapter
      addItemDecoration(MarginItemDecoration(top = 8.px, bottom = 8.px))
      addOnScrollListener(EndlessScrollListener { viewModel.publishViewIntent(LoadNextPage) })
    }
  }

  override fun render(state: State) {
    refresh_layout.isRefreshing = state.firstPageLoading

    if (state.success) listAdapter.submitList(state.instruments)
    state.error?.message?.let { snackBarShort(it)?.show() }
  }
}


