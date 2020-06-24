package com.musicgear.gas.data.api

import com.musicgear.gas.data.api.okhttp.VkApiClientFactory
import com.musicgear.gas.data.api.okhttp.interceptors.AcceptLanguageInterceptor
import com.musicgear.gas.data.api.okhttp.interceptors.AccessTokenInterceptor
import com.musicgear.gas.data.api.okhttp.interceptors.ConnectionStateInterceptor
import com.musicgear.gas.data.api.retrofit.GasApi
import com.musicgear.gas.data.api.retrofit.converter.GasConverterFactory
import com.musicgear.gas.data.datasource.VkSessionSource
import com.musicgear.gas.data.utils.mockStringResponse
import io.mockk.mockk
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class AccessTokenInterceptorTest: AutoCloseKoinTest() {

  private val accessToken = "testing_access_token"

  private lateinit var server: MockWebServer
  private lateinit var api: GasApi
  private val converterFactory: Converter.Factory = GasConverterFactory()
  private val callAdapterFactory: CallAdapter.Factory = RxJava2CallAdapterFactory.create()

  private val sessionSource: VkSessionSource = mockk(relaxed = true)

  private val connectionStateInterceptor: ConnectionStateInterceptor = mockk(relaxed = true)
  private val acceptLanguageInterceptor: AcceptLanguageInterceptor = mockk(relaxed = true)
  private val tokenInterceptor = AccessTokenInterceptor(sessionSource)

  @Before
  fun setup() {
    server = MockWebServer()
    initApi()
  }

  private fun initApi() {
    val retrofit = Retrofit.Builder()
      .baseUrl(server.url("/"))
      .addConverterFactory(converterFactory)
      .client(
        VkApiClientFactory(
          connectionStateInterceptor,
          tokenInterceptor,
          acceptLanguageInterceptor
        ).create()
      )
      .addCallAdapterFactory(callAdapterFactory)
      .build()

    api = retrofit.create(GasApi::class.java)
  }

  @Test
  fun `token interceptor adds access token to request url`() {
    val response = mockStringResponse {
      serverResponseCode = 200
    }
  }
}