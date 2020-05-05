package com.musicgear.gas.utils.basecomponents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.musicgear.gas.utils.basecomponents.mvi.BaseView
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import com.musicgear.gas.utils.rx.disposeOf
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<State, StateChange, VM : BaseViewModel<State, StateChange>> :
  Fragment(),
  BaseView<State> {

  protected var createdFirstTime: Boolean = true
  protected open val viewSubscriptions: CompositeDisposable? = null
  protected abstract val viewModel: VM
  protected abstract fun layoutResId(): Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    createdFirstTime = savedInstanceState == null
    observeViewState()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(layoutResId(), container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initIntents()
  }

  override fun observeViewState() {
    viewModel.currentViewState().observe(this, Observer { state -> render(state) })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    viewSubscriptions.disposeOf()
  }
}

abstract class BaseBindingFragment<Binding : ViewDataBinding,
    State,
    StateChange,
    VM : BaseViewModel<State, StateChange>> :
  BaseFragment<State, StateChange, VM>() {

  protected var viewBinding: Binding? = null
    private set

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = DataBindingUtil.inflate<Binding>(inflater, layoutResId(), container, false).run {
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