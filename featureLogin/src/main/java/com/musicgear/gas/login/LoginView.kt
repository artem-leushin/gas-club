package com.musicgear.gas.login

import android.app.Activity
import com.musicgear.gas.domain.entity.AuthBundle

interface LoginView {
  data class State(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: Throwable? = null
  )

  sealed class Event {
    class StartLogin(val activity: Activity) : Event()
    class ProceedLogin(val vkAuthBundle: AuthBundle) : Event()
  }

  sealed class StateChange {
    object StartLoading : StateChange()
    object Success : StateChange()
    class Error(val error: Throwable) : StateChange()
    object HideError : StateChange()
    class Transition(transition: () -> Unit) : StateChange() {
      init {
        transition()
      }
    }
  }
}