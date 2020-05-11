package com.musicgear.gas.domain.interactor

import android.app.Activity
import com.musicgear.gas.domain.entity.AuthBundle
import com.musicgear.gas.domain.entity.SessionStatus
import com.musicgear.gas.domain.service.AuthService
import com.musicgear.gas.domain.service.SessionStatusService
import io.reactivex.Completable
import io.reactivex.Observable

class LoginWithVkUseCase(
  private val authService: AuthService
) {
  fun execute(activity: Activity): Completable = authService.startLogin(activity)
}

class ProceedLoginWithVkUseCase(
  private val authService: AuthService
) {
  fun execute(authBundle: AuthBundle): Completable = authService.proceedLogin(authBundle)
}

class CheckIfUserIsLoggedInUseCase(
  private val sessionStatusRepo: SessionStatusService
) {
  fun execute(): Observable<SessionStatus> = sessionStatusRepo.observeSessionStatus()
}

class LogoutUseCase(
  private val authService: AuthService
) {
  fun execute(): Completable = authService.logout()
}
