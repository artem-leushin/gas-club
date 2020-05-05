package com.musicgear.gas.data.service

import android.app.Activity
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.datasource.VkSessionSource
import com.musicgear.gas.domain.entity.AuthBundle
import com.musicgear.gas.domain.entity.VkSession
import com.musicgear.gas.domain.exception.VkLoginFailedException
import com.musicgear.gas.domain.service.AuthService
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

class AuthServiceImpl(private val sessionSource: VkSessionSource) : AuthService {

  override fun startLogin(activity: Activity): Completable = Completable.fromCallable {
    VK.login(activity, listOf(VKScope.GROUPS, VKScope.PHOTOS))
  }

  override fun proceedLogin(authBundle: AuthBundle): Completable =
    Observable.create<VkSession> { emitter ->
      authBundle.run { VK.onActivityResult(requestCode, resultCode, data, authCallback(emitter)) }
    }.flatMapCompletable { sessionSource.saveSession(it) }

  override fun logout(): Completable = Completable.fromCallable { VK.logout() }

  private val authCallback: (ObservableEmitter<VkSession>) -> VKAuthCallback = { emitter ->
    object : VKAuthCallback {
      override fun onLogin(token: VKAccessToken) = emitter
        .onNext(token.toDomain())

      override fun onLoginFailed(errorCode: Int) = emitter
        .onError(VkLoginFailedException(errorCode))
    }
  }
}



