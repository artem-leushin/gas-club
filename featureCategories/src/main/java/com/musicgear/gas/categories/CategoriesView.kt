package com.musicgear.gas.categories

import com.musicgear.gas.categories.CategoriesView.State
import com.musicgear.gas.utils.basecomponents.mvi.BaseView

interface CategoriesView : BaseView<State> {
  data class State(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: Throwable? = null,
    val categories: List<DisplayableCategory> = emptyList()
  )

  sealed class Event {
    object RefreshCategories : Event()
    class GoToInstruments(val id: Int) : Event()
  }

  sealed class StateChange {
    object StartLoading : StateChange()
    object Success : StateChange()
    class CategoriesLoaded(val categories: List<DisplayableCategory>) : StateChange()
    class Error(val error: Throwable) : StateChange()
    object HideError : StateChange()
    class Transition(transition: () -> Unit) : StateChange() {
      init {
        transition()
      }
    }
  }

  data class DisplayableCategory(
    val id: Int,
    val name: String,
    val description: String,
    val instrumentsCount: String,
    val photoUrl: String
  )
}