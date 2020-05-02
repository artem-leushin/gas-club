package com.musicgear.gas

import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import io.reactivex.Observable

class MainViewModel : BaseViewModel<MainView.State, MainView.StateChanges>() {
  override fun initState(): MainView.State {
    TODO("not implemented")
  }

  override fun viewIntents(intentStream: Observable<*>): Observable<MainView.StateChanges> {
    TODO("not implemented")
  }

  override fun viewModelIntents(): Observable<MainView.StateChanges> {
    return super.viewModelIntents()
  }

  override fun reduceState(state: MainView.State, changes: MainView.StateChanges): MainView.State {
    TODO("not implemented")
  }
}