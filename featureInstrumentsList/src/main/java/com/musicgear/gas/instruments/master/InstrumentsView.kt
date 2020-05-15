package com.musicgear.gas.instruments.master

import android.os.Parcelable
import android.view.View
import com.musicgear.gas.instruments.master.InstrumentsView.Displayable.DisplayableInstrument
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
    class GoToDetails(
      val instrument: DisplayableInstrument,
      val sharedViews: Pair<View, String>,
      val position: Int
    ) : Intent()

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
    abstract val id: Int
    abstract val transitionName: String

    @Parcelize
    data class DisplayableInstrument(
      override val id: Int,
      val userId: Int,
      val description: String,
      val dateAdded: String,
      val photoUrl: String
    ) : Displayable(), Parcelable {
      override val transitionName = "$id $dateAdded"
    }

    object ProgressItem : Displayable() {
      override val id = -1
      override val transitionName = ""
    }
  }
}
