package com.musicgear.gas.data.service

import androidx.test.filters.SmallTest
import com.musicgear.gas.data.datasource.VkFacade
import com.musicgear.gas.domain.entity.SessionStatus
import com.musicgear.gas.domain.service.SessionStatusService
import io.mockk.every
import io.mockk.mockk
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

@SmallTest
class SessionStatusServiceTest {

  private lateinit var service: SessionStatusService
  private val facade: VkFacade = mockk(relaxed = true)

  @Before
  fun setUp() {
    service = SessionStatusServiceImpl(facade)
  }

  @Test
  fun `user is logged out on start`() {
    service.observeSessionStatus()
      .test()
      .assertValueCount(1)
      .assertValue(SessionStatus.LOGGED_OUT)
  }

  @Test
  fun `login status is changed after user logs in`() {
    every { facade.isLoggedIn() } returns false andThen true

    val observer = TestObserver<SessionStatus>()

    service.observeSessionStatus()
      .subscribe(observer)

    observer
      .assertValueCount(1)
      .assertValue(SessionStatus.LOGGED_OUT)

    observer
      .assertValueCount(1)
      .assertValue(SessionStatus.LOGGED_IN)
  }
}