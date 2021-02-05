package com.musicgear.gas.blacklist

import com.musicgear.gas.blacklist.BlacklistedUsersView.State
import com.musicgear.gas.blacklist.BlacklistedUsersView.StateChange
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import io.reactivex.Observable

class BlacklistedUsersViewModel : BaseViewModel<State, StateChange>(State()) {

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChange> =
    Observable.never()

  override fun reduce(state: State, changes: StateChange): State {
    return state
  }
}