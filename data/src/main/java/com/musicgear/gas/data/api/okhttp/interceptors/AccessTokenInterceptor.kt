package com.musicgear.gas.data.api.okhttp.interceptors

import com.musicgear.gas.data.BuildConfig
import com.musicgear.gas.data.datasource.VkSessionSource
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal class AccessTokenInterceptor(sessionSource: VkSessionSource) : Interceptor {

  private val accessToken: String by lazy { sessionSource.getSession().blockingFirst().accessToken }

  override fun intercept(chain: Interceptor.Chain): Response {
    val origRequest = chain.request()
    val urlWithToken = includeAccessTokenToUrl(origRequest.url)
    return chain.proceed(origRequest.setNewUrl(urlWithToken))
  }

  private fun includeAccessTokenToUrl(url: HttpUrl): HttpUrl =
    url.newBuilder()
      .addQueryParameter("access_token", accessToken)
      .addQueryParameter("v", BuildConfig.API_VERSION)
      .build()

  private fun Request.setNewUrl(url: HttpUrl): Request = newBuilder().url(url).build()
}