package com.musicgear.gas.main

import com.musicgear.gas.main.MainView.Intent
import com.musicgear.gas.main.MainView.State
import com.musicgear.gas.main.MainView.StateChanges
import com.musicgear.gas.main.MainView.StateChanges.ContolsVisible
import com.musicgear.gas.main.MainView.StateChanges.ControlsHidden
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import io.reactivex.Observable

class MainViewModel : BaseViewModel<State, StateChanges>() {
  override fun initState(): State = State()

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChanges> =
    with(intentStream) {
      Observable.merge(
        ofType(Intent.ShowControls::class.java)
          .map { ContolsVisible },

        ofType(Intent.HideControls::class.java)
          .map { ControlsHidden }
      )
    }

  override fun reduceState(state: State, changes: StateChanges): State = when (changes) {
    is ContolsVisible -> state.copy(hideControls = false, displayControls = true)
    is ControlsHidden -> state.copy(hideControls = true, displayControls = false)
  }
}