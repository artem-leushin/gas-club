package com.musicgear.gas.data.api

import com.musicgear.gas.data.api.okhttp.ResponseFactory
import com.musicgear.gas.data.api.okhttp.interceptors.ConnectionStateInterceptor
import com.musicgear.gas.domain.service.InternetObserverService
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.internal.http.RealInterceptorChain
import org.junit.Assert
import org.junit.Test

class ConnectionInterceptorTest {

  private val internetObserver: InternetObserverService = mockk(relaxed = true)
  private lateinit var connectionStateInterceptor: Interceptor

  private val originalChain: Interceptor.Chain = spyk<RealInterceptorChain>()
  private val originalRequest = mockk<Request>(relaxed = true)

  private val responseFactory = ResponseFactory
  private val errorResponse = responseFactory.createErrorResponse(originalRequest)
  private val successResponse = responseFactory.createSuccessResponse(originalRequest)

  @Test
  fun `connection state interceptor returns error response on disconnect`() {
    every { internetObserver.observeInternetConnection() } returns Observable.just(false)
    every { originalChain.request() } returns originalRequest

    connectionStateInterceptor = ConnectionStateInterceptor(internetObserver, responseFactory)

    val response = connectionStateInterceptor.intercept(originalChain)

    Assert.assertTrue(response.code == errorResponse.code)
    Assert.assertTrue(response.message == errorResponse.message)

    verify(exactly = 0) { originalChain.proceed(originalRequest) }
  }

  @Test
  fun `connection state interceptor proceeds original request if internet connected`() {
    every { originalChain.proceed(originalRequest) } returns successResponse
    every { internetObserver.observeInternetConnection() } returns Observable.just(true)
    every { originalChain.request() } returns originalRequest

    connectionStateInterceptor = ConnectionStateInterceptor(internetObserver, responseFactory)

    val response = connectionStateInterceptor.intercept(originalChain)

    Assert.assertTrue(response.code == successResponse.code)
    Assert.assertTrue(response.message == successResponse.message)

    verify(exactly = 1) { originalChain.proceed(originalRequest) }
  }
}