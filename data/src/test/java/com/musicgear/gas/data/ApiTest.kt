package com.musicgear.gas.data

import com.musicgear.gas.data.api.okhttp.ClientFactory
import com.musicgear.gas.data.api.retrofit.RetrofitApi
import com.musicgear.gas.data.datasource.VkSessionSource
import com.musicgear.gas.domain.service.InternetObserverService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

internal class ApiTest : KoinTest {
  private lateinit var server: MockWebServer
  private lateinit var api: RetrofitApi

  private val sessionSource: VkSessionSource = get()
  private val converterFactory: Converter.Factory = get()
  private val callAdapterFactory: CallAdapter.Factory = get()
  private val internetObserver: InternetObserverService = mock()

  @Before
  fun setup() {
    whenever(internetObserver.observeInternetConnection()).thenReturn(Observable.just(true))
    server = MockWebServer()
    initApi()
  }

  private fun initApi() {
    val retrofit = Retrofit.Builder()
      .baseUrl(server.url("").toString())
      .addConverterFactory(converterFactory)
      .client(ClientFactory(internetObserver, sessionSource).create())
      .addCallAdapterFactory(callAdapterFactory)
      .build()

    api = retrofit.create(RetrofitApi::class.java)
  }

  @Test
  fun `User request`() {

  }

}

//internal fun mockedResponse(init: MockProtocolResponse.() -> Unit): MockResponse {
//  val builder = MockProtocolResponse()
//  builder.init()
//  return builder.build()
//}
//
//internal class MockProtocolResponse {
//  var serverResponseCode: Int = 0
//  var apiAction: String = ""
//  var responseBody: Any = Any()
//
//  private lateinit var responseBodyType: Type
//  private lateinit var responseString: String
//
//  fun build(): MockResponse = MockResponse().apply {
//    setResponseCode(serverResponseCode)
//    responseBodyType = responseBody.javaClass
//
//    responseString = when (serverResponseCode) {
//      200 -> EncodingUtil.encodeBase64(createResponseString())
//      else -> createResponseString()
//    }
//    setBody(responseString)
//  }
//
//  private fun createResponseString(): String =
//    PlainStringResponseConverter(
//      responseBodyType
//    ).convert(responseBody)!!
//}
