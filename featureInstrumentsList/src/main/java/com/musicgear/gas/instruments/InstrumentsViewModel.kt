package com.musicgear.gas.instruments

import com.musicgear.gas.domain.entity.Instrument
import com.musicgear.gas.domain.interactor.LoadInstrumentsUseCase
import com.musicgear.gas.instruments.InstrumentsView.Displayable.DisplayableInstrument
import com.musicgear.gas.instruments.InstrumentsView.Displayable.ProgressItem
import com.musicgear.gas.instruments.InstrumentsView.Intent.GoToDetails
import com.musicgear.gas.instruments.InstrumentsView.Intent.LoadNextPage
import com.musicgear.gas.instruments.InstrumentsView.Intent.RefreshInstruments
import com.musicgear.gas.instruments.InstrumentsView.State
import com.musicgear.gas.instruments.InstrumentsView.StateChange
import com.musicgear.gas.instruments.InstrumentsView.StateChange.Error
import com.musicgear.gas.instruments.InstrumentsView.StateChange.FirstPageLoading
import com.musicgear.gas.instruments.InstrumentsView.StateChange.HideError
import com.musicgear.gas.instruments.InstrumentsView.StateChange.InstrumentsLoaded
import com.musicgear.gas.instruments.InstrumentsView.StateChange.NextPageLoading
import com.musicgear.gas.instruments.InstrumentsView.StateChange.Transition
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import com.musicgear.gas.utils.rx.applySchedulers
import com.musicgear.gas.utils.rx.handleError
import io.reactivex.Observable

class InstrumentsViewModel(
  private val loadInstruments: LoadInstrumentsUseCase,
  private val coordinator: InstrumentsCoordinator
) : BaseViewModel<State, StateChange>() {
  override fun initState(): State = State()

  var currentInstrumentPosition: Int = 0
  var categoryId: Int = 0

  private val defaultPerPage = 20
  private var firstPage = 0
  private var currentPage = 0
  private var loadingPage: Boolean = false
  private var endReached: Boolean = false

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChange> =
    with(intentStream) {
      Observable.merge(
        ofType(GoToDetails::class.java)
          .map { Transition { coordinator.goToItemDetails(it.instrument) } },

        ofType(RefreshInstruments::class.java)
          .doOnNext {
            currentPage = firstPage
            loadingPage = true
          }
          .switchMap { loadInstruments(page = firstPage).startWith(FirstPageLoading) },

        ofType(LoadNextPage::class.java)
          .filter { loadingPage.not() && endReached.not() }
          .doOnNext { loadingPage = true }
          .switchMap { loadInstruments(page = currentPage).startWith(NextPageLoading) }
      )
    }

  override fun viewModelIntents(): Observable<StateChange> = loadInstruments(firstPage)
    .startWith(FirstPageLoading)

  private fun loadInstruments(page: Int): Observable<StateChange> =
    loadInstruments.execute(categoryId, page)
      .applySchedulers()
      .doOnNext {
        endReached = it.size < defaultPerPage
        currentPage++
      }
      .map(::mapToDisplayable)
      .map { InstrumentsLoaded(it) }
      .cast(StateChange::class.java)
      .handleError { listOf(Error(it), HideError) }

  private fun mapToDisplayable(instruments: List<Instrument>): List<DisplayableInstrument> =
    instruments.map { it.toPresentation() }

  private fun Instrument.toPresentation() = DisplayableInstrument(
    id, name, dateAdded.toString(), photoSizes.first().srcUrl
  )

  override fun reduce(state: State, changes: StateChange): State = when (changes) {
    is FirstPageLoading -> state.copy(firstPageLoading = true, error = null)
    is NextPageLoading -> state.copy(
      nextPageLoading = true,
      error = null,
      instruments = state.instruments + ProgressItem
    )
    is InstrumentsLoaded -> state.copy(
      success = true,
      firstPageLoading = false,
      nextPageLoading = false,
      error = null,
      instruments = changes.instruments
    )
    is Error -> state.copy(
      success = false,
      nextPageLoading = false,
      firstPageLoading = false,
      error = changes.error
    )
    is HideError -> state.copy(error = null)
    else -> state
  }
}

