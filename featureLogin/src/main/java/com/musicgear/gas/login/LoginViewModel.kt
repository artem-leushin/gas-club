package com.musicgear.gas.login

import com.musicgear.gas.domain.interactor.LoginWithVkUseCase
import com.musicgear.gas.domain.interactor.ProceedLoginWithVkUseCase
import com.musicgear.gas.login.LoginView.Event.ProceedLogin
import com.musicgear.gas.login.LoginView.Event.StartLogin
import com.musicgear.gas.login.LoginView.State
import com.musicgear.gas.login.LoginView.StateChange
import com.musicgear.gas.login.LoginView.StateChange.Error
import com.musicgear.gas.login.LoginView.StateChange.HideError
import com.musicgear.gas.login.LoginView.StateChange.StartLoading
import com.musicgear.gas.login.LoginView.StateChange.Success
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import com.musicgear.gas.utils.rx.andThenAction
import com.musicgear.gas.utils.rx.applySchedulers
import com.musicgear.gas.utils.rx.handleError
import io.reactivex.Observable

class LoginViewModel(
  private val startLogin: LoginWithVkUseCase,
  private val proceedLogin: ProceedLoginWithVkUseCase,
  private val coordinator: LoginCoordinator
) : BaseViewModel<State, StateChange>() {
  override fun initState(): State = State()

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChange> =
    with(intentStream) {
      Observable.merge(
        ofType(StartLogin::class.java)
          .switchMap {
            startLogin.execute(it.activity)
              .applySchedulers()
              .andThenAction { StateChange.Idle }
              .cast(StateChange::class.java)
              .handleError { listOf(Error(it), HideError) }
              .startWith(StartLoading)
          },

        ofType(ProceedLogin::class.java)
          .switchMap {
            proceedLogin.execute(it.vkAuthBundle)
              .applySchedulers()
              .andThenAction { StateChange.Transition(coordinator::goToMusicGear) }
              .cast(StateChange::class.java)
              .handleError { listOf(Error(it), HideError) }
              .startWith(StartLoading)
          }
      )
    }

  override fun reduce(state: State, changes: StateChange): State = when (changes) {
    is StartLoading -> state.copy(loading = true)
    is Success -> state.copy(success = true, loading = false, error = null)
    is Error -> state.copy(success = false, loading = false, error = changes.error)
    is HideError -> state.copy(error = null)
    else -> state
  }
}