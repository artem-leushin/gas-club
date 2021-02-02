package com.musicgear.gas.utils.basecomponents

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import com.musicgear.gas.utils.basecomponents.mvi.BaseView.BaseSingleEvent
import com.musicgear.gas.utils.basecomponents.mvi.BaseView.BaseSingleEvent.Idle
import com.musicgear.gas.utils.basecomponents.mvi.BaseViewModel
import com.musicgear.gas.utils.rx.disposeOf
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ru.tinkoff.accounting.mvi.SingleLiveEvent

abstract class SingleEventsViewModel<State>(initialState: State) :
  BaseViewModel<State, Any>(initialState) {

  private val singleEvents: SingleLiveEvent<BaseSingleEvent> = SingleLiveEvent()
  private var singleEventsDisposable: Disposable? = null

  fun singleEvents(): LiveData<BaseSingleEvent> = singleEvents

  protected open fun mapToSingleEvent(changes: Any): BaseSingleEvent = Idle

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  private fun observeStateChanges() {
    singleEventsDisposable = stateChanges
      .map { mapToSingleEvent(it) }
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { singleEvent -> singleEvents.value = singleEvent }
  }

  override fun onCleared() {
    super.onCleared()
    singleEventsDisposable.disposeOf()
  }
}