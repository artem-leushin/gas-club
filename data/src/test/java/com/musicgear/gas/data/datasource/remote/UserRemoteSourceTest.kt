package com.musicgear.gas.data.datasource.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.musicgear.gas.data.api.retrofit.GasApi
import com.musicgear.gas.data.datasource.UserSource
import com.musicgear.gas.data.entity.remote.UserR
import com.musicgear.gas.data.entity.remote.UserResponse
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.entity.User
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class UserRemoteSourceTest {

  private val api: GasApi = mockk(relaxed = true)
  private lateinit var source: UserSource

  private val expectedUser = UserR(11, "Artem", "Leushin", "artem_leushin", "www.avatar.com")

  @Before
  fun setup() {
    source = UserRemoteSource(api)
  }

  @Test
  fun `source returns domain user from api`() {
    val response = UserResponse(listOf(expectedUser))
    every { api.getUser() } returns Observable.just(response)

    source.getUser()
      .test()
      .assertValue(expectedUser.toDomain())
  }

  @Test
  fun `source returns empty domain user if api returns null`() {
    val emptyResponse = UserResponse(null)
    every { api.getUser() } returns Observable.just(emptyResponse)

    source.getUser()
      .test()
      .assertValue(User.EMPTY)
  }
}