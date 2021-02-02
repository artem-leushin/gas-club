package com.musicgear.gas.utils.basecomponents.mvi

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.musicgear.gas.utils.rx.disposed
import com.musicgear.gas.utils.testing.OpenForTesting
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

@OpenForTesting
abstract class BaseViewModel<State, StateChange>(private val initialState: State) : ViewModel(),
  LifecycleObserver {

  protected var stateChanges: Observable<StateChange> = Observable.empty()

  private var states = MutableLiveData<State>().distinctUntilChanged() as MutableLiveData<State>
  private val viewIntentsConsumer: Relay<Any> = PublishRelay.create()
  private var intentsDisposable: Disposable? = null

  fun viewIntentsConsumer() = viewIntentsConsumer
  fun currentViewState(): LiveData<State> = states
  fun sendIntent(intent: Any) {
    if (intentsDisposable.disposed())
      startNewStream()

    viewIntentsConsumer.accept(intent)
  }

  abstract fun viewIntents(intentStream: Observable<*>): Observable<StateChange>
  protected fun startUpIntents(): Observable<StateChange> = Observable.never()
  protected abstract fun reduce(state: State, changes: StateChange): State

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  private fun initStream() {
    if (intentsDisposable.disposed())
      startNewStream()
  }

  private fun startNewStream() {
    stateChanges = Observable.merge(
      viewIntents(viewIntentsConsumer),
      startUpIntents()
    ).share()

    intentsDisposable = stateChanges.scan(initialState, ::reduce)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { state -> states.value = state }
  }

  override fun onCleared() {
    intentsDisposable?.dispose()
  }
}