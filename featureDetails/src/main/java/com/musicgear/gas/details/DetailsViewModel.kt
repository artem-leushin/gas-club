package com.musicgear.gas.details

import com.musicgear.gas.details.DetailsView.State
import com.musicgear.gas.details.DetailsView.StateChange
import com.musicgear.gas.domain.interactor.LoadFirstCommentUseCase
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import io.reactivex.Observable

class DetailsViewModel(
  private val loadFirstComment: LoadFirstCommentUseCase
) : BaseViewModel<State, StateChange>() {
  override fun initState(): State = State()

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChange> =
    Observable.never()

  override fun reduce(state: State, changes: StateChange): State {
    return state
  }
}