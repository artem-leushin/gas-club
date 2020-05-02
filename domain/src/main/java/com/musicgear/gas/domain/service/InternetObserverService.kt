package com.musicgear.gas.domain.service

import io.reactivex.Observable

interface InternetObserverService {
  fun observeInternetConnection(): Observable<Boolean>
}