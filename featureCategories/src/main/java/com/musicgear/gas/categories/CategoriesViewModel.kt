package com.musicgear.gas.categories

import com.musicgear.gas.categories.CategoriesView.DisplayableCategory
import com.musicgear.gas.categories.CategoriesView.Event
import com.musicgear.gas.categories.CategoriesView.State
import com.musicgear.gas.categories.CategoriesView.StateChange
import com.musicgear.gas.categories.CategoriesView.StateChange.CategoriesLoaded
import com.musicgear.gas.categories.CategoriesView.StateChange.Error
import com.musicgear.gas.categories.CategoriesView.StateChange.HideError
import com.musicgear.gas.categories.CategoriesView.StateChange.StartLoading
import com.musicgear.gas.categories.CategoriesView.StateChange.Success
import com.musicgear.gas.categories.CategoriesView.StateChange.Transition
import com.musicgear.gas.domain.entity.Category
import com.musicgear.gas.domain.interactor.LoadCategoriesUseCase
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import com.musicgear.gas.utils.rx.applySchedulers
import com.musicgear.gas.utils.rx.handleError
import io.reactivex.Observable

class CategoriesViewModel(
  private val loadCategories: LoadCategoriesUseCase,
  private val coordinator: CategoriesCoordinator
) : BaseViewModel<State, StateChange>() {
  override fun initState(): State = State()

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChange> =
    with(intentStream) {
      Observable.merge(
        ofType(Event.GoToInstruments::class.java)
          .map { Transition(coordinator::goToInstruments) },

        ofType(Event.RefreshCategories::class.java)
          .switchMap { loadCategories() }
      )
    }

  override fun viewModelIntents(): Observable<StateChange> = loadCategories()

  private fun loadCategories(): Observable<StateChange> = loadCategories.execute()
    .applySchedulers()
    .map(::mapToDisplayable)
    .map { CategoriesLoaded(it) }
    .cast(StateChange::class.java)
    .handleError { listOf(Error(it), HideError) }
    .startWith(StartLoading)

  private fun mapToDisplayable(categories: List<Category>): List<DisplayableCategory> =
    categories.map { category ->
      category.run {
        DisplayableCategory(id, name, description, instrumentsCount.toString(), photoUrl)
      }
    }

  override fun reduceState(state: State, changes: StateChange): State = when (changes) {
    is StartLoading -> state.copy(loading = true, error = null)
    is Success -> state.copy(loading = false, success = true, error = null)
    is CategoriesLoaded -> state.copy(
      categories = changes.categories,
      loading = false,
      success = true,
      error = null
    )
    is Error -> state.copy(loading = false, success = false, error = changes.error)
    is HideError -> state.copy(error = null)
    else -> state
  }
}