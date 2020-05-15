package com.musicgear.gas.instruments.details

import com.musicgear.gas.instruments.master.InstrumentsView
import com.musicgear.gas.utils.basecomponents.mvi.BaseView

interface DetailsView : BaseView<DetailsView.State> {
  data class State(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: Throwable? = null,
    val details: DisplayableDetails = DisplayableDetails()
  )

  sealed class Intent {
    class LoadDetails(val instrument: InstrumentsView.Displayable.DisplayableInstrument) : Intent()
  }

  sealed class StateChange {
    object StartLoading : StateChange()
    class DetailsLoadSuccess(val details: DisplayableDetails) : StateChange()
    class Error(val error: Throwable) : StateChange()
    object HideError : StateChange()
  }

  data class DisplayableDetails(
    val photoDescription: String = "",
    val firstUserComments: String = ""
  ) {
    val fullText = buildString {
      appendln(photoDescription)
      appendln(firstUserComments)
    }
  }
}