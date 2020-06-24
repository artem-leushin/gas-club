package com.musicgear.gas.data.service

import android.content.Context
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy
import com.musicgear.gas.domain.service.InternetObserverService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

internal class InternetObserverImpl(
  private val connectivityStreamFactory: ConnectivityStreamFactory
) : InternetObserverService {

  override fun observeInternetConnection(): Observable<Boolean> =
    connectivityStreamFactory.createNetworkStream().switchMap {
      if (it.state() == NetworkInfo.State.CONNECTED)
        connectivityStreamFactory.createInternetStream()
          .filter { it }
          .take(1)
      else Observable.just(false)
    }
      .subscribeOn(Schedulers.io())
}


class ConnectivityStreamFactory(private val context: Context) {
  fun createNetworkStream(): Observable<Connectivity> = ReactiveNetwork.observeNetworkConnectivity(context)
  fun createInternetStream(): Observable<Boolean> = ReactiveNetwork.observeInternetConnectivity(HostSettings.get())

  private object HostSettings {
    private const val hostGoogle = "https://google.com"

    fun get(): InternetObservingSettings = InternetObservingSettings.builder()
      .host(hostGoogle)
      .strategy(SocketInternetObservingStrategy())
      .build()
  }
}

