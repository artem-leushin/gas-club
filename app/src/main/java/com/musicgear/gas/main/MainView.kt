package com.musicgear.gas.main

import com.musicgear.gas.main.MainView.State
import com.musicgear.gas.main.MainView.StateChange.BottomAppBarMode
import com.musicgear.gas.utils.basecomponents.mvi.BaseView

interface MainView : BaseView<State> {
  data class State(
    val hideControls: Boolean = true,
    val displayControls: Boolean = false,
    val screenMode: BottomAppBarMode = BottomAppBarMode.None,
    val changeScreenMode: Boolean = false
  )

  sealed class Intent {
    object ShowControls : Intent()
    object HideControls : Intent()
    class SwitchToMode(val mode: BottomAppBarMode) : Intent()
    object Logout : Intent()
  }

  sealed class StateChange {
    object Idle : StateChange()
    object ControlsVisible : StateChange()
    object ControlsHidden : StateChange()

    sealed class BottomAppBarMode : StateChange() {
      object PostPhoto : BottomAppBarMode()
      object TalkToSeller : BottomAppBarMode()
      object None : BottomAppBarMode()
    }

    class Transition(transition: () -> Unit) : StateChange() {
      init {
        transition()
      }
    }

    class Error(val error: Throwable) : StateChange()
    object HideError : StateChange()
  }

}