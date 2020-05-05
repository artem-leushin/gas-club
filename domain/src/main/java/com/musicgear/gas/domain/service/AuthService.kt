package com.musicgear.gas.domain.service

import android.app.Activity
import com.musicgear.gas.domain.entity.AuthBundle
import io.reactivex.Completable

interface AuthService {
  fun startLogin(activity: Activity): Completable
  fun proceedLogin(authBundle: AuthBundle): Completable
  fun logout(): Completable
}
