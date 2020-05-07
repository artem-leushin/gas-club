package com.musicgear.gas.utils.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

inline fun navigationObservable(crossinline transition: () -> Any): Observable<Any> =
  Observable.fromCallable { transition() }
    .subscribeOn(AndroidSchedulers.mainThread())

fun <StateChange> sequenceEvents(vararg stateChange: StateChange): Observable<StateChange> =
  Observable.fromIterable(stateChange.asIterable())

//region SCHEDULERS
inline fun <T> Observable<T>.applySchedulers(): Observable<T> =
  subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

inline fun <T> Single<T>.applySchedulers(): Single<T> =
  subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

inline fun Completable.applySchedulers(): Completable =
  subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

inline fun <T> Flowable<T>.applySchedulers(): Flowable<T> =
  subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
//endregion

inline fun <reified T> Observable<T>.castAndApplySchedulers(): Observable<T> = applySchedulers()
  .cast(T::class.java)

inline fun <reified SC> Observable<SC>.handleError(crossinline func: (Throwable) -> List<SC>): Observable<SC> =
  onErrorResumeNext { e: Throwable ->
    sequenceEvents(*func(e).toTypedArray())
  }

inline fun <reified T> Completable.andThenAction(crossinline source: () -> T): Observable<T> =
  andThen(Observable.fromCallable { source() })
