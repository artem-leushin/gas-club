package com.musicgear.gas.instruments

import com.musicgear.gas.instruments.InstrumentsView.State
import com.musicgear.gas.instruments.InstrumentsView.StateChange
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import io.reactivex.Observable

class InstrumentsViewModel : BaseViewModel<State, StateChange>() {
  override fun initState(): State = State()

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChange> = Observable.never()

  override fun reduceState(
    state: State,
    changes: StateChange
  ): State {
    return state
  }
}