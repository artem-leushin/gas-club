package com.musicgear.gas.gear

import com.musicgear.gas.gear.MusicGearView.*
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import io.reactivex.Observable

class MusicGearViewModel : BaseViewModel<State, StateChange>() {
  override fun initState(): State = State()

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChange> = Observable.never()

  override fun reduceState(
    state: State,
    changes: StateChange
  ): State {
    return state
  }
}