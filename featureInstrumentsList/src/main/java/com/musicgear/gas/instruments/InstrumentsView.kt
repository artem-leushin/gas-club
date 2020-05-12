package com.musicgear.gas.instruments

import android.os.Parcelable
import com.musicgear.gas.instruments.InstrumentsView.Displayable.DisplayableInstrument
import kotlinx.android.parcel.Parcelize

interface InstrumentsView {
  data class State(
    val firstPageLoading: Boolean = false,
    val nextPageLoading: Boolean = false,
    val success: Boolean = false,
    val error: Throwable? = null,
    val instruments: List<Displayable> = emptyList()
  )

  sealed class Intent {
    object RefreshInstruments : Intent()
    object LoadNextPage : Intent()
    class GoToDetails(val instrument: DisplayableInstrument) : Intent()
  }

  sealed class StateChange {
    object FirstPageLoading : StateChange()
    object NextPageLoading : StateChange()
    class FirstPageSuccess(val instruments: List<DisplayableInstrument>) : StateChange()
    class NextPageSuccess(val instruments: List<DisplayableInstrument>) : StateChange()
    class Error(val error: Throwable) : StateChange()
    object HideError : StateChange()
    class Transition(transition: () -> Unit) : StateChange() {
      init {
        transition()
      }
    }
  }

  sealed class Displayable {
    @Parcelize
    data class DisplayableInstrument(
      val id: Int,
      val title: String,
      val dateAdded: String,
      val photoUrl: String
    ) : Displayable(), Parcelable

    object ProgressItem : Displayable()
  }
}
