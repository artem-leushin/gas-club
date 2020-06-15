package com.musicgear.gas.data.datasource.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.musicgear.gas.data.database.daos.VkSessionDao
import com.musicgear.gas.data.datasource.VkSessionSource
import com.musicgear.gas.data.entity.local.VkSessionDB
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.entity.VkSession
import com.vk.api.sdk.VKTokenExpiredHandler
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class VkSessionLocalSourceTest {

  private val dao: VkSessionDao = mockk(relaxed = true)
  private lateinit var source: VkSessionSource
  private lateinit var tokenExpiryHandler: VKTokenExpiredHandler

  private val expectedSession = VkSessionDB(11, "email", "phone", "11_22_33")

  @Before
  fun setup() {
    source = VkSessionLocalSource(dao)
    tokenExpiryHandler = source as VKTokenExpiredHandler
  }

  @Test
  fun `source returns domain session from dao`() {
    every { dao.getSession() } returns Observable.just(listOf(expectedSession))

    source.getSession()
      .test()
      .assertValue(expectedSession.toDomain())
  }

  @Test
  fun `empty source returns empty session from dao`() {
    every { dao.getSession() } returns Observable.just(emptyList())

    source.getSession()
      .test()
      .assertValue(VkSession.EMPTY)
  }

  @Test
  fun `source saves domain user into dao`() {
    every { dao.insert(expectedSession) } returns Completable.complete()

    source.insert(expectedSession.toDomain())
      .test()
      .assertComplete()

    verify { dao.insert(expectedSession) }
  }

  @Test
  fun `source deletes session from dao`() {
    var subscribed = false
    val daoDeleteSession = Completable.complete()
      .doOnSubscribe { subscribed = true }

    every { dao.delete() } returns daoDeleteSession

    source.clear()
      .test()
      .assertComplete()

    verify { dao.delete() }
    Assert.assertTrue(subscribed)
  }

  @Test
  fun `source deletes session when VK token expired`() {
    var subscribed = false
    val daoDeleteSession = Completable.complete()
      .doOnSubscribe { subscribed = true }

    every { dao.delete() } returns daoDeleteSession
    tokenExpiryHandler.onTokenExpired()

    verify { dao.delete() }
    Assert.assertTrue(subscribed)
  }
}