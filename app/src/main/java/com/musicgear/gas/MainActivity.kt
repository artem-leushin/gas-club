package com.musicgear.gas

import android.os.Bundle
import com.musicgear.gas.MainView.*
import com.musicgear.gas.utils.basecomponents.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<State, StateChanges, MainViewModel>(), MainView {

  override val viewModel: MainViewModel by viewModel()
  override val viewSubscriptions: Disposable = CompositeDisposable()

  override fun layoutResId(): Int = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun initIntents() {
  }

  override fun observeViewState() {
  }

  override fun render(state: State) {
  }
}

