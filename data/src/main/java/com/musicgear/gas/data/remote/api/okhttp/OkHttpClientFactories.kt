package com.musicgear.gas.data.remote.api.okhttp

import android.annotation.SuppressLint
import android.util.Log
import com.musicgear.gas.data.HttpClientBuilderProvider
import com.musicgear.gas.data.remote.api.entity.ErrorHolder
import com.musicgear.gas.domain.service.InternetObserverService
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol.HTTP_1_1
import okhttp3.Request
import okhttp3.Response
import okhttp3.Response.Builder
import okhttp3.ResponseBody.Companion.toResponseBody
import java.util.Locale
import java.util.concurrent.TimeUnit

interface OkHttpClientFactory {
  fun create(): OkHttpClient
}

@SuppressLint("CheckResult")
internal class ClientFactory(connectivityService: InternetObserverService) : OkHttpClientFactory {

  private var connected = true

  init {
    connectivityService.observeInternetConnection()
      .subscribe { connected = it }
  }

  override fun create(): OkHttpClient = HttpClientBuilderProvider.create()
    .addInterceptor(acceptLanguageInterceptor)
    .addInterceptor(connectionStateInterceptor)
    .build()

  private val acceptLanguageInterceptor = Interceptor { chain ->
    val origRequest = chain.request()
    val langTag = Locale.getDefault().toLanguageTag()
    val localizedRequest = origRequest.newBuilder()
      .addHeader("Accept-Language", langTag)
      .build()

    chain.proceed(localizedRequest)
  }

  private val connectionStateInterceptor = Interceptor { chain ->
    with(chain.request()) {
      if (connected) chain.proceed(this)
      else buildConnectionResponse(this)
    }
  }

  private fun buildConnectionResponse(request: Request): Response = Builder()
    .request(request)
    .code(503)
    .message("No internet connection. Request failed")
    .protocol(HTTP_1_1)
    .body(ErrorHolder.noInternetConnection.toResponseBody("application/json".toMediaTypeOrNull()))
    .build()
}

internal class ImageLoaderClientFactory : OkHttpClientFactory {
  override fun create(): OkHttpClient =
    OkHttpClient.Builder()
      .connectTimeout(30, TimeUnit.SECONDS)
      .readTimeout(15, TimeUnit.SECONDS)
      .build()
}