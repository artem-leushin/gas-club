package com.musicgear.gas.data.api

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.musicgear.gas.data.api.okhttp.ResponseFactory
import com.musicgear.gas.data.api.okhttp.VkApiClientFactory
import com.musicgear.gas.data.api.okhttp.interceptors.AcceptLanguageInterceptor
import com.musicgear.gas.data.api.okhttp.interceptors.AccessTokenInterceptor
import com.musicgear.gas.data.api.okhttp.interceptors.ConnectionStateInterceptor
import com.musicgear.gas.data.api.retrofit.GasApi
import com.musicgear.gas.data.api.retrofit.converter.GasConverterFactory
import com.musicgear.gas.data.datasource.VkSessionSource
import com.musicgear.gas.data.entity.remote.AlbumR
import com.musicgear.gas.data.entity.remote.AlbumsResponse
import com.musicgear.gas.data.entity.remote.BaseResponse
import com.musicgear.gas.data.entity.remote.SizeR
import com.musicgear.gas.data.entity.remote.UserR
import com.musicgear.gas.data.entity.remote.UserResponse
import com.musicgear.gas.data.utils.mockObjectResponse
import com.musicgear.gas.data.utils.mockStringResponse
import com.musicgear.gas.domain.entity.VkSession
import com.musicgear.gas.domain.service.InternetObserverService
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

@RunWith(AndroidJUnit4::class)
@MediumTest
internal class ApiTest {
  private lateinit var server: MockWebServer
  private lateinit var api: GasApi

  private val accessToken = "testing_access_token"

  private val sessionSource: VkSessionSource = mockk(relaxed = true)
  private val internetObserver: InternetObserverService = mockk(relaxed = true)
  private val converterFactory: Converter.Factory = GasConverterFactory()
  private val callAdapterFactory: CallAdapter.Factory = RxJava2CallAdapterFactory.create()
  private val connectionStateInterceptor = ConnectionStateInterceptor(internetObserver, ResponseFactory)
  private val acceptLanguageInterceptor = AcceptLanguageInterceptor()
  private val tokenInterceptor = AccessTokenInterceptor(sessionSource)

  @Before
  fun setup() {
    every { sessionSource.getSession() } returns Observable.just(VkSession(accessToken = accessToken))
    every { internetObserver.observeInternetConnection() } returns Observable.just(true)
    server = MockWebServer()
    initApi()
  }

  private fun initApi() {
    val retrofit = Retrofit.Builder()
      .baseUrl(server.url("/method/"))
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
    val responseBody = "{test: 1}"
    val expectedResponse = mockStringResponse {
      serverResponseCode = 200
      responseBodyString = responseBody
    }

    server.enqueue(expectedResponse)

    api.getUser().test()

    val actualUrl = server.takeRequest().requestUrl!!

    Assert.assertTrue(actualUrl.queryParameterNames.contains("access_token"))
    Assert.assertTrue(actualUrl.queryParameter("access_token").equals(accessToken))

  }

  @Test
  fun `user request returns valid user on success`() {
    val user = UserR(
      id = 1,
      firstName = "Yabee",
      lastName = "DabeeDoo",
      screenName = "yabeedabeedoo",
      photoUrl = "https://photo.url"
    )

    val expectedBody = UserResponse(listOf(user))
    val mockedServerResponse = mockObjectResponse {
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
    Assert.assertTrue(recordedUrl.queryParameterNames.contains("fields"))
    Assert.assertEquals(
      listOf("screen_name, photo_200_orig"),
      recordedUrl.queryParameterValues("fields")
    )
  }

  @Test
  fun `albums request returns valid albums on success`() {
    val albums = listOf(
      AlbumR(1, "title", "desc", 11, "url", listOf(SizeR("1"))),
      AlbumR(2, "title2", "desc2", 22, "url2", listOf(SizeR("2")))
    )

    val expectedBody: BaseResponse<AlbumsResponse> = BaseResponse(AlbumsResponse(albums.size, albums))
    val mockedServerResponse = mockObjectResponse {
      serverResponseCode = 200
      responseBody = expectedBody
    }

    server.enqueue(mockedServerResponse)

    api.getAlbums()
      .map { it.response!!.items }
      .test()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValue(albums)

    val recordedUrl = server.takeRequest().requestUrl!!

    Assert.assertTrue(recordedUrl.encodedPathSegments.contains("photos.getAlbums"))
    Assert.assertTrue(recordedUrl.queryParameterNames.contains("fields"))
  }
}