package com.musicgear.gas.categories

import com.musicgear.gas.categories.CategoriesView.Event
import com.musicgear.gas.categories.CategoriesView.State
import com.musicgear.gas.categories.CategoriesView.StateChange
import com.musicgear.gas.categories.CategoriesView.StateChange.CategoriesLoaded
import com.musicgear.gas.categories.CategoriesView.StateChange.Error
import com.musicgear.gas.categories.CategoriesView.StateChange.HideError
import com.musicgear.gas.categories.CategoriesView.StateChange.StartLoading
import com.musicgear.gas.categories.CategoriesView.StateChange.Success
import com.musicgear.gas.domain.interactor.LoadCategoriesUseCase
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import com.musicgear.gas.utils.rx.applySchedulers
import com.musicgear.gas.utils.rx.sequenceEvents
import io.reactivex.Observable

class CategoriesViewModel(
  private val loadCategories: LoadCategoriesUseCase
) : BaseViewModel<State, StateChange>() {
  override fun initState(): State = State()

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChange> =
    intentStream.ofType(Event.GoToInstrumentDetails::class.java)
      .switchMap { Observable.just(Success) }

  override fun viewModelIntents(): Observable<StateChange> = loadCategories.execute()
    .applySchedulers()
    .map { CategoriesLoaded(it) }
    .cast(StateChange::class.java)
    .onErrorResumeNext { e: Throwable -> sequenceEvents(Error(e), HideError) }
    .startWith(StartLoading)

  override fun reduceState(state: State, changes: StateChange): State {
    return state
  }
}