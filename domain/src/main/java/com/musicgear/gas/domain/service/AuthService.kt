package com.musicgear.gas.domain.service

import android.app.Activity
import com.musicgear.gas.domain.entity.AuthBundle
import com.musicgear.gas.domain.entity.VkSession
import io.reactivex.Completable
import io.reactivex.Observable

interface AuthService {
  fun startLogin(activity: Activity): Completable
  fun proceedLogin(authBundle: AuthBundle): Completable
  fun logout(): Completable
}
