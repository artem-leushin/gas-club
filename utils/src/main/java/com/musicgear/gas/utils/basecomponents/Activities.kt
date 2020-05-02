package com.musicgear.gas.utils.basecomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.musicgear.gas.utils.basecomponents.mvi.BaseView
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import com.musicgear.gas.utils.rx.disposeOf
import io.reactivex.disposables.Disposable

abstract class BaseActivity<State, StateChange, VM : BaseViewModel<State, StateChange>> :
  AppCompatActivity(),
  BaseView<State> {

  protected var createdFirstTime: Boolean = true
  protected open val viewSubscriptions: Disposable? = null
  protected abstract val viewModel: VM
  protected abstract fun layoutResId(): Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    createdFirstTime = savedInstanceState == null
    onSetContentView(savedInstanceState)
    initIntents()
    observeViewState()
  }

  override fun onDestroy() {
    super.onDestroy()
    viewSubscriptions.disposeOf()
  }

  protected open fun onSetContentView(savedInstanceState: Bundle?) {
    setContentView(layoutResId())
  }
}

abstract class BaseBindingActivity<
    Binding : ViewDataBinding,
    State,
    StateChange,
    VM : BaseViewModel<State, StateChange>> : BaseActivity<State, StateChange, VM>() {

  protected lateinit var binding: Binding
    private set

  override fun onSetContentView(savedInstanceState: Bundle?) {
    binding = DataBindingUtil.setContentView(this, layoutResId())
  }
}