package com.musicgear.gas.categories

import com.musicgear.gas.categories.CategoriesView.State
import com.musicgear.gas.domain.entity.Category
import com.musicgear.gas.utils.basecomponents.mvi.BaseView

interface CategoriesView : BaseView<State> {
  data class State(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: Throwable? = null,
    val categories: List<Category> = emptyList()
  )

  sealed class Event {
    object LoadCategories : Event()
    class GoToInstrumentDetails(val id: Int) : Event()
  }

  sealed class StateChange {
    object StartLoading : StateChange()
    object Success : StateChange()
    class CategoriesLoaded(val categories: List<Category>) : StateChange()
    class Error(val error: Throwable) : StateChange()
    object HideError : StateChange()
  }
}