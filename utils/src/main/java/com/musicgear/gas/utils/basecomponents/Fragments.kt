package com.musicgear.gas.utils.basecomponents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.musicgear.gas.utils.basecomponents.mvi.BaseView
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import com.musicgear.gas.utils.rx.disposeOf
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<State, VM>(@LayoutRes layoutId: Int) : Fragment(layoutId),
  BaseView<State>
    where VM : BaseViewModel<State, Any> {

  protected var viewSubscriptions: CompositeDisposable? = null
  protected abstract val viewModel: VM

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    observeViewState()
    (viewModel as? SingleEventsViewModel<*>)?.observeSingleEvents()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewSubscriptions = CompositeDisposable()
    initIntents()
  }

  override fun observeViewState() {
    viewModel.currentViewState().observe(this, Observer { state -> render(state) })
    lifecycle.addObserver(viewModel)
  }

  private fun SingleEventsViewModel<*>.observeSingleEvents() {
    singleEvents()
      .observe(this@BaseFragment, Observer { event -> renderSingleEvent(event) })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    viewSubscriptions.disposeOf()
    viewSubscriptions = null
  }
}

abstract class BaseBindingFragment<Binding : ViewDataBinding,
    State,
    StateChange,
    VM : BaseViewModel<State, Any>>(@LayoutRes private val layoutId: Int) :
  BaseFragment<State, VM>(layoutId) {

  protected var viewBinding: Binding? = null
    private set

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = DataBindingUtil.inflate<Binding>(inflater, layoutId, container, false).run {
    viewBinding = this
    root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initIntents()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    viewBinding = null
  }
}