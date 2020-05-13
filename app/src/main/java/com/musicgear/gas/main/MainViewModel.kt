package com.musicgear.gas.main

import com.musicgear.gas.domain.interactor.LogoutUseCase
import com.musicgear.gas.main.MainView.Intent
import com.musicgear.gas.main.MainView.State
import com.musicgear.gas.main.MainView.StateChange
import com.musicgear.gas.main.MainView.StateChange.BottomAppBarMode
import com.musicgear.gas.main.MainView.StateChange.BottomAppBarMode.None
import com.musicgear.gas.main.MainView.StateChange.BottomAppBarMode.PostPhoto
import com.musicgear.gas.main.MainView.StateChange.BottomAppBarMode.TalkToSeller
import com.musicgear.gas.main.MainView.StateChange.ControlsHidden
import com.musicgear.gas.main.MainView.StateChange.ControlsVisible
import com.musicgear.gas.main.MainView.StateChange.Error
import com.musicgear.gas.main.MainView.StateChange.HideError
import com.musicgear.gas.main.MainView.StateChange.Idle
import com.musicgear.gas.main.MainView.StateChange.Transition
import com.musicgear.gas.navigation.MainCoordinator
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import com.musicgear.gas.utils.rx.andThenAction
import com.musicgear.gas.utils.rx.applySchedulers
import com.musicgear.gas.utils.rx.handleError
import io.reactivex.Observable

class MainViewModel(
  private val logout: LogoutUseCase,
  private val coordinator: MainCoordinator
) : BaseViewModel<State, StateChange>() {
  override fun initState(): State = State()

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChange> =
    with(intentStream) {
      Observable.merge(
        ofType(Intent.Logout::class.java)
          .switchMap {
            logout.execute()
              .applySchedulers()
              .andThenAction { Transition(coordinator::logout) }
              .cast(StateChange::class.java)
              .handleError { listOf(Error(it), HideError) }
          },

        ofType(Intent.ShowControls::class.java)
          .map { ControlsVisible },

        ofType(Intent.HideControls::class.java)
          .map { ControlsHidden },

        ofType(Intent.SwitchToMode::class.java)
          .switchMap { Observable.just(it.mode, Idle) }
      )
    }

  override fun reduce(state: State, changes: StateChange): State = when (changes) {
    is ControlsVisible -> state.copy(hideControls = false, displayControls = true)
    is ControlsHidden -> state.copy(hideControls = true, displayControls = false)
    is PostPhoto,
    is TalkToSeller,
    is None -> state.copy(screenMode = changes as BottomAppBarMode, changeScreenMode = true)
    is Idle -> state.copy(changeScreenMode = false)
    else -> state
  }
}