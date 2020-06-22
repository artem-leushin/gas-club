package com.musicgear.gas.data.service

import android.app.Activity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.musicgear.gas.data.datasource.VkFacade
import com.musicgear.gas.data.datasource.VkSessionSource
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.entity.VkSession
import com.musicgear.gas.domain.service.AuthService
import com.vk.api.sdk.auth.VKAccessToken
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Emitter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class AuthServiceTest {

  private val accessToken: VKAccessToken = mockk(relaxed = true)
  private val source: VkSessionSource = mockk(relaxed = true)
  private val vk: VkFacade = mockk(relaxed = true)

  private lateinit var service: AuthService
  private val activity: Activity = mockk(relaxed = true)

  @Before
  fun setup() {
    service = AuthServiceImpl(source, vk)
  }

  @Test
  fun `vk login is started`() {
    service.startLogin(activity)
      .test()
      .assertComplete()

    verify { vk.login(activity) }
  }

  @Test
  fun `vk callback emits access token and completes observable`() {
    val vkSessionEmitter: Emitter<VkSession> = mockk(relaxed = true)
    val vkAuthCallback = AuthServiceImpl.VkEmittingCallback(vkSessionEmitter)

    vkAuthCallback.onLogin(accessToken)

    verify(exactly = 1) { vkSessionEmitter.onNext(accessToken.toDomain()) }
    verify { vkSessionEmitter.onComplete() }
  }

  @Test
  fun `vk callback signals error and completes observable`() {
    val errorCode = 101
    val vkSessionEmitter: Emitter<VkSession> = mockk(relaxed = true)
    val vkAuthCallback = AuthServiceImpl.VkEmittingCallback(vkSessionEmitter)

    vkAuthCallback.onLoginFailed(errorCode)

    verify { vkSessionEmitter.onError(any()) }
  }

  @Test
  fun `access token is cleared on vk logout`() {
    every { vk.logout() } returns Unit
    every { source.clear() } returns Completable.complete()

    service.logout()
      .test()
      .assertComplete()

    verify { vk.logout() }
    verify { source.clear() }
  }
}