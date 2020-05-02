package com.musicgear.gas.data.api.internet

import android.content.Context
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy
import com.musicgear.gas.domain.service.InternetObserverService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

internal class InternetObserverImpl constructor(
  private val context: Context
) : InternetObserverService {

  override fun observeInternetConnection(): Observable<Boolean> =
    ReactiveNetwork.observeNetworkConnectivity(context)
      .switchMap {
        if (it.state() == NetworkInfo.State.CONNECTED)
          ReactiveNetwork.observeInternetConnectivity(HostSettings.get())
            .filter { it }
            .take(1)
        else Observable.just(false)
      }
      .subscribeOn(Schedulers.io())
}

private object HostSettings {
  private val hostGoogle = "https://google.com"

  fun get(): InternetObservingSettings = InternetObservingSettings.builder()
    .host(hostGoogle)
    .strategy(SocketInternetObservingStrategy())
    .build()
}
