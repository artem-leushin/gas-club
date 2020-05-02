package com.musicgear.gas.utils.basecomponents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.musicgear.gas.utils.basecomponents.mvi.BaseView
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import com.musicgear.gas.utils.rx.disposeOf
import io.reactivex.disposables.Disposable

abstract class BaseFragment<State, StateChange, VM : BaseViewModel<State, StateChange>> :
  Fragment(),
  BaseView<State> {

  protected var createdFirstTime: Boolean = true
  protected open var viewSubscriptions: Disposable? = null
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
  ): View? {
    val view = inflater.inflate(layoutResId(), container, false)
    initIntents()
    return view
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

  override fun onDestroyView() {
    super.onDestroyView()
    viewBinding = null
  }
}