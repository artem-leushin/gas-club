package com.musicgear.gas.data.api.okhttp.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

internal class AcceptLanguageInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val origRequest = chain.request()
    val langTag = Locale.getDefault().toLanguageTag()
    val localizedRequest = origRequest.newBuilder()
      .addHeader("Accept-Language", langTag)
      .build()

    return chain.proceed(localizedRequest)
  }
}