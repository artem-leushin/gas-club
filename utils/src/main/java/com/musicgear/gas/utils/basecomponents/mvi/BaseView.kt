package com.musicgear.gas.utils.basecomponents.mvi

interface BaseView<in State> {
  fun initIntents()
  fun observeViewState()
  fun render(state: State)
}