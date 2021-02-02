package com.musicgear.gas.utils.basecomponents.mvi

interface BaseView<in State> {
  fun initIntents()
  fun observeViewState()
  fun render(state: State)
  fun renderSingleEvent(event: BaseSingleEvent) {}

  interface BaseSingleEvent {
    class ShowError(val error: Throwable) : BaseSingleEvent
    object Idle : BaseSingleEvent
  }
}