package com.musicgear.gas.categories

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import com.musicgear.gas.categories.CategoriesView.State
import com.musicgear.gas.categories.CategoriesView.StateChange
import com.musicgear.gas.categories.adapter.CategoriesAdapter
import com.musicgear.gas.categories.adapter.HorizontalDividerDecoration
import com.musicgear.gas.categories.adapter.MarginItemDecoration
import com.musicgear.gas.utils.basecomponents.BaseFragment
import com.musicgear.gas.utils.px
import com.musicgear.gas.utils.snackBarShort
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_categories.refresh_layout
import kotlinx.android.synthetic.main.fragment_categories.rv_categories
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : BaseFragment<State, StateChange, CategoriesViewModel>(), CategoriesView {

  private lateinit var listAdapter: CategoriesAdapter
  override val viewModel: CategoriesViewModel by viewModel()
  override val viewSubscriptions: CompositeDisposable = CompositeDisposable()

  override fun layoutResId(): Int = R.layout.fragment_categories

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initRecyclerView()
  }

  override fun initIntents() {
    viewSubscriptions += refresh_layout.refreshes()
      .map { CategoriesView.Event.RefreshCategories }
      .subscribe(viewModel.viewIntentsConsumer())
  }

  private fun initRecyclerView() {
    this.listAdapter = CategoriesAdapter(viewModel::publishViewIntent)

    rv_categories.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = listAdapter
      addItemDecoration(MarginItemDecoration(top = 8.px, bottom = 8.px))
      addItemDecoration(HorizontalDividerDecoration(context))
    }
  }

  override fun render(state: State) {
    refresh_layout.isRefreshing = state.loading

    if (state.success) listAdapter.submitList(state.categories)
    state.error?.message?.let { snackBarShort(it)?.show() }
  }
}

