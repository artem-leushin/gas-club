package com.musicgear.gas.data.service

import android.annotation.SuppressLint
import com.musicgear.gas.domain.entity.SessionStatus
import com.musicgear.gas.domain.entity.SessionStatus.LOGGED_IN
import com.musicgear.gas.domain.entity.SessionStatus.LOGGED_OUT
import com.musicgear.gas.domain.service.SessionStatusService
import com.vk.api.sdk.VK
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SessionStatusServiceImpl : SessionStatusService {

  private val sessionPublisher = BehaviorSubject.createDefault(LOGGED_OUT)

  @SuppressLint("CheckResult")
  override fun observeSessionStatus(): Observable<SessionStatus> {
    isLoggedInWithVk()
      .subscribe { loggedIn: Boolean ->
        when (loggedIn) {
          true -> sessionPublisher.onNext(LOGGED_IN)
          false -> sessionPublisher.onNext(LOGGED_OUT)
        }
      }
    return sessionPublisher.distinctUntilChanged()
  }

  private fun isLoggedInWithVk(): Observable<Boolean> = Observable.fromCallable { VK.isLoggedIn() }
}