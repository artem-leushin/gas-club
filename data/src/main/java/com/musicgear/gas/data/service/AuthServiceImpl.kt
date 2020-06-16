package com.musicgear.gas.data.service

import android.app.Activity
import com.musicgear.gas.data.datasource.VkFacade
import com.musicgear.gas.data.datasource.VkSessionSource
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.entity.AuthBundle
import com.musicgear.gas.domain.entity.VkSession
import com.musicgear.gas.domain.exception.VkLoginFailedException
import com.musicgear.gas.domain.service.AuthService
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import io.reactivex.Completable
import io.reactivex.Emitter
import io.reactivex.Observable

// TODO: 15.06.2020 maybe pull VK-related logic up to presentation layer? This way we could avoid mixing android-specific dependencies with domain (Activity & Intent)

class AuthServiceImpl(
  private val sessionSource: VkSessionSource,
  private val vkFacade: VkFacade
) : AuthService {

  override fun startLogin(activity: Activity): Completable = Completable.fromCallable {
    vkFacade.login(activity)
  }

  override fun proceedLogin(authBundle: AuthBundle): Completable = Observable.create<VkSession> { emitter ->
    vkFacade.handleAuthResultFromActivity(authBundle, VkEmittingCallback.create(emitter))
  }
    .flatMapCompletable { sessionSource.insert(it) }

  override fun logout(): Completable = Completable.fromCallable { vkFacade.logout() }
    .andThen(sessionSource.clear())

  class VkEmittingCallback(private val emitter: Emitter<VkSession>) : VKAuthCallback {

    companion object {
      @JvmStatic
      fun create(emitter: Emitter<VkSession>): VkEmittingCallback = VkEmittingCallback(emitter)
    }

    override fun onLogin(token: VKAccessToken) {
      emitter.onNext(token.toDomain())
      emitter.onComplete()
    }

    override fun onLoginFailed(errorCode: Int) = emitter.onError(VkLoginFailedException(errorCode))
  }
}