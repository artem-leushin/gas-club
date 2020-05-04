package com.musicgear.gas.login

import com.musicgear.gas.login.LoginView.*
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import io.reactivex.Observable

class LoginViewModel : BaseViewModel<State, StateChange>() {
  override fun initState(): State = State()

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChange> = Observable.never()

  override fun reduceState(
    state: State,
    changes: StateChange
  ): State {
    return state
  }
}