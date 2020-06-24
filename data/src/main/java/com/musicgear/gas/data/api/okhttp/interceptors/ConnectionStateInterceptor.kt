package com.musicgear.gas.data.api.okhttp.interceptors

import com.musicgear.gas.data.api.okhttp.HttpResponseFactory
import com.musicgear.gas.domain.service.InternetObserverService
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal class ConnectionStateInterceptor(
  connectivityService: InternetObserverService,
  private val responseFactory: HttpResponseFactory
) : Interceptor {

  private var connected = true

  init {
    connectivityService.observeInternetConnection()
      .subscribe { connected = it }
  }

  override fun intercept(chain: Interceptor.Chain): Response = chain.request().run {
    if (connected) chain.proceed(this)
    else buildConnectionErrorResponse(this)
  }

  private fun buildConnectionErrorResponse(request: Request): Response = responseFactory.createErrorResponse(request)
}

