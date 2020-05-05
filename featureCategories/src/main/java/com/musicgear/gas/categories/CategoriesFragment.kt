package com.musicgear.gas.categories

import com.musicgear.gas.categories.CategoriesView.State
import com.musicgear.gas.categories.CategoriesView.StateChange
import com.musicgear.gas.utils.basecomponents.BaseFragment
import com.musicgear.gas.utils.snackBarShort
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : BaseFragment<State, StateChange, CategoriesViewModel>(),
  CategoriesView {

  override val viewModel: CategoriesViewModel by viewModel()
  override val viewSubscriptions: CompositeDisposable = CompositeDisposable()

  override fun layoutResId(): Int = R.layout.fragment_categories

  override fun initIntents() {
    viewModel.viewIntentsConsumer()
  }

  override fun render(state: State) {
    state.error?.message?.let { snackBarShort(it)?.show() }
  }
}

