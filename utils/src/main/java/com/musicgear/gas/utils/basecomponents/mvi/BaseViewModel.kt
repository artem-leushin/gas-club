package com.musicgear.gas.utils.basecomponents.mvi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<State, StateChange> : ViewModel() {
  private var states = MutableLiveData<State>()
  private val viewIntentsConsumer: Relay<Any> = PublishRelay.create()
  private val sideEffectRelay: Relay<StateChange> = PublishRelay.create()
  private var intentsDisposable: Disposable? = null

  protected abstract fun initState(): State

  private fun initIntentsStream() {
    intentsDisposable = Observable.merge(
      viewIntents(viewIntentsConsumer),
      viewModelIntents(),
      sideEffectRelay
    ).scan(initState()) { previousState, stateChanges ->
      reduce(previousState, stateChanges)
    }
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { state -> states.value = state }
  }

  abstract fun viewIntents(intentStream: Observable<*>): Observable<StateChange>

  protected open fun viewModelIntents(): Observable<StateChange> = Observable.never()
  protected open fun viewModelIntents(sideEffects: Relay<StateChange>): Observable<StateChange> = Observable.never()

  protected abstract fun reduce(state: State, changes: StateChange): State

  fun viewIntentsConsumer() = viewIntentsConsumer.also {
    if (intentsDisposable == null)
      initIntentsStream()
  }

  fun currentViewState(): LiveData<State> = states
  fun publishViewIntent(intent: Any) = viewIntentsConsumer.accept(intent)
  fun publishStateChange(change: StateChange) = sideEffectRelay.accept(change)

  override fun onCleared() {
    intentsDisposable?.dispose()
  }
}