package com.musicgear.gas.details

import com.musicgear.gas.details.MusicItemDetailsView.*
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import io.reactivex.Observable

class MusicItemDetailsViewModel : BaseViewModel<State, StateChange>() {
  override fun initState(): State = State()

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChange> =
    Observable.never()

  override fun reduceState(state: State, changes: StateChange): State {
    return state
  }
}