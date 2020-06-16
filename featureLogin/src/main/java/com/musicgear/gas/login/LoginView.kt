package com.musicgear.gas.login

import android.app.Activity
import com.musicgear.gas.domain.entity.AuthBundle
import com.musicgear.gas.login.LoginView.State
import com.musicgear.gas.utils.basecomponents.mvi.BaseView

interface LoginView : BaseView<State> {
  data class State(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: Throwable? = null
  )

  sealed class Intent {
    class StartLogin(val activity: Activity) : Intent()
    class ProceedLogin(val vkAuthBundle: AuthBundle) : Intent()
  }

  sealed class StateChange {
    object StartLoading : StateChange()
    object Idle : StateChange()
    object Success : StateChange()
    data class Error(val error: Throwable) : StateChange()
    object HideError : StateChange()
    class Transition(transition: () -> Unit) : StateChange() {
      init {
        transition()
      }
    }
  }
}