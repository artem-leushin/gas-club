package com.musicgear.gas.data.service

import android.net.NetworkInfo
import androidx.test.filters.SmallTest
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.musicgear.gas.data.utils.RxTestSchedulerRule
import com.musicgear.gas.domain.service.InternetObserverService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
class InternetObserverTest {

  @get:Rule
  open val rxRule = RxTestSchedulerRule()

  private lateinit var internetObserver: InternetObserverService
  private var networkStream: Subject<Connectivity> = PublishSubject.create()
  private var internetStream: Subject<Boolean> = PublishSubject.create()

  private val factory: ConnectivityStreamFactory = mockk()
  private val connected = Connectivity.state(NetworkInfo.State.CONNECTED).build()
  private val disconnected = Connectivity.state(NetworkInfo.State.DISCONNECTED).build()

  @Before
  fun setUp() {
    every { factory.createNetworkStream() } returns networkStream
    every { factory.createInternetStream() } returns internetStream
    internetObserver = InternetObserverImpl(factory)
  }

  @Test
  fun `service does not subscribe for the internet and immediately returns false, if network has been disconnected `() {
    val observer = TestObserver<Boolean>()

    internetObserver.observeInternetConnection()
      .subscribe(observer)

    networkStream.onNext(disconnected)
    networkStream.onNext(disconnected)

    observer.assertValueCount(2)
      .assertValues(false, false)
      .assertNotComplete()

    verify(exactly = 1) { factory.createNetworkStream() }
    verify(exactly = 0) { factory.createInternetStream() }
  }

  @Test
  fun `service subscribes for the internet, waits for connection and unsubscribes, if network has been connected`() {
    val observer = TestObserver<Boolean>()

    internetObserver.observeInternetConnection()
      .subscribeOn(Schedulers.single())
      .subscribe(observer)

    networkStream.onNext(connected)
    internetStream.onNext(true)

    observer.assertValueCount(1)
      .assertValue(true)
      .assertNotComplete()

    verify(exactly = 1) { factory.createNetworkStream() }
    verify(exactly = 1) { factory.createInternetStream() }
  }
}