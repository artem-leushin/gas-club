package com.musicgear.gas.data

import com.musicgear.gas.data.api.okhttp.ClientFactory
import com.musicgear.gas.data.api.retrofit.GasApi
import com.musicgear.gas.data.api.retrofit.converter.GasConverterFactory
import com.musicgear.gas.data.datasource.VkSessionSource
import com.musicgear.gas.data.entity.remote.UserR
import com.musicgear.gas.data.entity.remote.UserResponse
import com.musicgear.gas.domain.entity.VkSession
import com.musicgear.gas.domain.service.InternetObserverService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

internal class ApiTest {
  private lateinit var server: MockWebServer
  private lateinit var api: GasApi

  private val accessToken = "testing_access_token"
  private val GAS_ID = GasApi.GAS_GROUP_ID

  private val sessionSource: VkSessionSource = mock()
  private val internetObserver: InternetObserverService = mock()
  private val converterFactory: Converter.Factory = GasConverterFactory()
  private val callAdapterFactory: CallAdapter.Factory = RxJava2CallAdapterFactory.create()

  @Before
  fun setup() {
    whenever(sessionSource.getSession()).thenReturn(Observable.just(VkSession(accessToken = accessToken)))
    whenever(internetObserver.observeInternetConnection()).thenReturn(Observable.just(true))
    server = MockWebServer()
    initApi()
  }

  private fun initApi() {
    val retrofit = Retrofit.Builder()
      .baseUrl(server.url("/method/"))
      .addConverterFactory(converterFactory)
      .client(ClientFactory(internetObserver, sessionSource).create())
      .addCallAdapterFactory(callAdapterFactory)
      .build()

    api = retrofit.create(GasApi::class.java)
  }

  @Test
  fun `Correctly formed User request returns valid user on success`() {
    val user = UserR(
      id = 1,
      firstName = "Yabee",
      lastName = "DabeeDoo",
      screenName = "yabeedabeedoo",
      photoUrl = "https://photo.url"
    )

    val expectedBody = UserResponse(listOf(user))
    val mockedServerResponse = mockedResponse {
      serverResponseCode = 200
      responseBody = expectedBody
    }

    server.enqueue(mockedServerResponse)

    api.getUser()
      .map { it.user }
      .test()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValue(listOf(user))

    val recordedUrl = server.takeRequest().requestUrl!!

    Assert.assertTrue(recordedUrl.encodedPathSegments.contains("users.get"))
    Assert.assertTrue(recordedUrl.queryParameterNames.contains("access_token"))
    Assert.assertEquals(listOf(accessToken), recordedUrl.queryParameterValues("access_token"))
    Assert.assertTrue(recordedUrl.queryParameterNames.contains("fields"))
    Assert.assertEquals(
      listOf("screen_name, photo_200_orig"),
      recordedUrl.queryParameterValues("fields")
    )
  }
}

internal class MockProtocolResponse {
  var serverResponseCode: Int = 0
  var responseBody: Any = Any()

  private lateinit var responseBodyType: Type
  private lateinit var responseString: String

  fun build(): MockResponse = MockResponse().apply {
    setResponseCode(serverResponseCode)
    responseBodyType = responseBody.javaClass

    setBody(responseString)
  }
}
