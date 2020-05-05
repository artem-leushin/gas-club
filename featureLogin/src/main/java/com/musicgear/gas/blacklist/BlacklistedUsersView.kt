package com.musicgear.gas.blacklist

import com.musicgear.gas.utils.basecomponents.mvi.BaseView

interface BlacklistedUsersView : BaseView<BlacklistedUsersView.State> {
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