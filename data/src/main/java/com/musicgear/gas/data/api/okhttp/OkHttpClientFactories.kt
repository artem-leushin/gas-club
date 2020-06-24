package com.musicgear.gas.data.api.okhttp

import android.annotation.SuppressLint
import com.musicgear.gas.data.HttpClientBuilderProvider
import com.musicgear.gas.data.api.okhttp.interceptors.AcceptLanguageInterceptor
import com.musicgear.gas.data.api.okhttp.interceptors.AccessTokenInterceptor
import com.musicgear.gas.data.api.okhttp.interceptors.ConnectionStateInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

interface OkHttpClientFactory {
  fun create(): OkHttpClient
}

@SuppressLint("CheckResult")
internal class VkApiClientFactory(
  private val connectionStateInterceptor: ConnectionStateInterceptor,
  private val accessTokenInterceptor: AccessTokenInterceptor,
  private val acceptLanguageInterceptor: AcceptLanguageInterceptor
) : OkHttpClientFactory {

  override fun create(): OkHttpClient = HttpClientBuilderProvider.create()
    .addInterceptor(accessTokenInterceptor)
    .addInterceptor(connectionStateInterceptor)
    .addInterceptor(acceptLanguageInterceptor)
    .build()
}

internal class ConversationsApiClientFactory(
  private val connectionStateInterceptor: ConnectionStateInterceptor
) : OkHttpClientFactory {

  override fun create(): OkHttpClient = HttpClientBuilderProvider.create()
    .addInterceptor(connectionStateInterceptor)
    .build()
}

internal class ImageLoaderClientFactory : OkHttpClientFactory {
  override fun create(): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(15, TimeUnit.SECONDS)
    .build()
}