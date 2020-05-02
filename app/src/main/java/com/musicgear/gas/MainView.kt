package com.musicgear.gas

import com.musicgear.gas.MainView.State
import com.musicgear.gas.utils.basecomponents.mvi.BaseView

interface MainView : BaseView<State> {
  sealed class StateChanges {
    object StartLoading : StateChanges()
    class Error(val error: Throwable) : StateChanges()
    object HideError : StateChanges()
  }

  sealed class Intent {

  }

  data class State(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: Throwable? = null
  )
}