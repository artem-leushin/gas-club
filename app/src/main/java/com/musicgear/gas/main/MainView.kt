package com.musicgear.gas.main

import com.musicgear.gas.main.MainView.State
import com.musicgear.gas.utils.basecomponents.mvi.BaseView

interface MainView : BaseView<State> {
  data class State(
    val hideControls: Boolean = true,
    val displayControls: Boolean = false
  )

  sealed class Intent {
    class ShowControls(val animate: Boolean = true) : Intent()
    class HideControls(val animate: Boolean = true) : Intent()
    object Logout : Intent()
  }

  sealed class StateChange {
    object ControlsVisible : StateChange()
    object ControlsHidden : StateChange()
    class Transition(transition: () -> Unit) : StateChange() {
      init {
        transition()
      }
    }

    class Error(val error: Throwable) : StateChange()
    object HideError : StateChange()
  }
}