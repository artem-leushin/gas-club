@file:Suppress("NOTHING_TO_INLINE")

package com.musicgear.gas.utils.rx

import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import com.jakewharton.rxbinding2.widget.afterTextChangeEvents
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.LazyThreadSafetyMode.NONE

inline fun <T> View.safeClicks(skip: Long = 300, noinline mapper: (Unit) -> T): Observable<T> =
  clicks().throttleFirst(skip, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).map(mapper)

inline fun View.safeClicks(skip: Long = 300): Observable<Unit> =
  clicks().throttleFirst(skip, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())

val editTextAfterTextChangeTransformer by lazy(NONE) {
  ObservableTransformer<TextViewAfterTextChangeEvent, String> {
    it.skip(1)
      .map { it.editable().toString() }
      .distinctUntilChanged()
      .debounce(350, TimeUnit.MILLISECONDS)
  }
}

inline fun <T> TextView.debouncedAfterTextChanges(noinline mapper: (String) -> T): Observable<T> =
  afterTextChangeEvents().compose(editTextAfterTextChangeTransformer).map(mapper)

inline fun Disposable?.disposeOf() {
  this?.let { if (isDisposed.not()) dispose() }

}

operator fun CompositeDisposable?.plusAssign(disposable: Disposable) {
  this?.let {
    add(disposable)
  }
}


