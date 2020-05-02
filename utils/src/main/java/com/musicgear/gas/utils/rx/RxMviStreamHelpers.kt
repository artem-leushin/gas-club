package com.musicgear.gas.utils.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

inline fun <T, reified R> Observable<T>.startWith(
  startWith: R,
  noinline errorHandler: (Throwable) -> Observable<R>
): Observable<R> =
  this.cast(R::class.java)
    .startWith(startWith)
    .onErrorResumeNext(errorHandler)

inline fun <E, reified C> E.after(
  delay: Long = 2,
  timeUnit: TimeUnit = TimeUnit.SECONDS,
  crossinline start: (Throwable) -> C
): Function<Throwable, Observable<C>> =
  Function { err: Throwable ->
    Observable.timer(delay, timeUnit)
      .map { this }
      .cast(C::class.java)
      .startWith(start(err))
  }

inline fun <E, reified C> C.then(
  delay: Long = 2,
  timeUnit: TimeUnit = TimeUnit.SECONDS,
  crossinline after: () -> E
): Observable<C> = Observable.timer(delay, timeUnit)
  .map { after() }
  .cast(C::class.java)
  .startWith(this)

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