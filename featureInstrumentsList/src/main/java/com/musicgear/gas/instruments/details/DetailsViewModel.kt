package com.musicgear.gas.instruments.details

import com.musicgear.gas.domain.entity.InstrumentDetails
import com.musicgear.gas.domain.interactor.LoadAuthorCommentsForInstrument
import com.musicgear.gas.instruments.details.DetailsView.DisplayableDetails
import com.musicgear.gas.instruments.details.DetailsView.Intent
import com.musicgear.gas.instruments.details.DetailsView.State
import com.musicgear.gas.instruments.details.DetailsView.StateChange
import com.musicgear.gas.instruments.details.DetailsView.StateChange.DetailsLoadSuccess
import com.musicgear.gas.instruments.details.DetailsView.StateChange.Error
import com.musicgear.gas.instruments.details.DetailsView.StateChange.HideError
import com.musicgear.gas.instruments.details.DetailsView.StateChange.StartLoading
import com.musicgear.gas.instruments.master.InstrumentsView
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import com.musicgear.gas.utils.rx.applySchedulers
import com.musicgear.gas.utils.rx.handleError
import io.reactivex.Observable

class DetailsViewModel(
  private val loadAuthorComments: LoadAuthorCommentsForInstrument
) : BaseViewModel<State, StateChange>() {
  override fun initState(): State = State()

  lateinit var instrument: InstrumentsView.Displayable.DisplayableInstrument

  override fun viewIntents(intentStream: Observable<*>): Observable<StateChange> =
    with(intentStream) {
      ofType(Intent.LoadDetails::class.java)
        .switchMap { intent ->
          loadAuthorComments.execute(intent.instrument.id, intent.instrument.userId)
            .applySchedulers()
            .map { DetailsLoadSuccess(it.toPresentation(intent.instrument.description)) }
            .cast(StateChange::class.java)
            .handleError { listOf(Error(it), HideError) }
            .startWith(StartLoading)
        }
    }

  private fun InstrumentDetails.toPresentation(photoDescription: String) =
    DisplayableDetails(photoDescription, text)

  override fun reduce(state: State, changes: StateChange): State = when (changes) {
    is StartLoading -> state.copy(loading = true)
    is DetailsLoadSuccess -> state.copy(
      details = changes.details,
      success = true,
      loading = false,
      error = null
    )
    is Error -> state.copy(
      success = false,
      loading = false,
      error = changes.error
    )
    is HideError -> state.copy(error = null)
  }
}