package com.musicgear.gas.details

import com.musicgear.gas.utils.basecomponents.mvi.BaseView

interface MusicItemDetailsView : BaseView<MusicItemDetailsView.State> {
  data class State(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: Throwable? = null
  )

  sealed class Event {

  }

  sealed class StateChange {
    object StartLoading : StateChange()
    class Error(val error: Throwable) : StateChange()
    object HideError : StateChange()
  }
}