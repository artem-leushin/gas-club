package com.musicgear.gas.di

import com.musicgear.gas.data.BuildConfig
import com.musicgear.gas.data.api.okhttp.ConversationsApiClientFactory
import com.musicgear.gas.data.api.okhttp.HttpResponseFactory
import com.musicgear.gas.data.api.okhttp.ImageLoaderClientFactory
import com.musicgear.gas.data.api.okhttp.OkHttpClientFactory
import com.musicgear.gas.data.api.okhttp.ResponseFactory
import com.musicgear.gas.data.api.okhttp.VkApiClientFactory
import com.musicgear.gas.data.api.okhttp.interceptors.AcceptLanguageInterceptor
import com.musicgear.gas.data.api.okhttp.interceptors.AccessTokenInterceptor
import com.musicgear.gas.data.api.okhttp.interceptors.ConnectionStateInterceptor
import com.musicgear.gas.data.api.retrofit.RetrofitApiFactory
import com.musicgear.gas.data.api.retrofit.converter.GasConverterFactory
import com.musicgear.gas.domain.constants.API_CONVERSATIONS
import com.musicgear.gas.domain.constants.API_VK
import com.musicgear.gas.domain.constants.HTTP_CLIENT_FACTORY_API_CONVERSATIONS
import com.musicgear.gas.domain.constants.HTTP_CLIENT_FACTORY_API_VK
import com.musicgear.gas.domain.constants.HTTP_CLIENT_FACTORY_IMG
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

val remoteApiModule = module {

  single(named(API_VK)) { BuildConfig.BASE_URL_VK }
  single(named(API_CONVERSATIONS)) { BuildConfig.BASE_URL_CONVERSATIONS }

  single {
    RetrofitApiFactory(
      baseUrl = get(named(API_VK)),
      gasHttpClientFactory = get(named(HTTP_CLIENT_FACTORY_API_VK)),
      converterFactory = get(),
      callAdapterFactory = get()
    ).createRetrofitApi()
  }

  single<HttpResponseFactory> { ResponseFactory }

  single {
    ConnectionStateInterceptor(
      connectivityService = get(),
      responseFactory = get()
    )
  }
  single {
    AccessTokenInterceptor(
      sessionSource = get()
    )
  }
  single { AcceptLanguageInterceptor() }

  single<OkHttpClientFactory>(named(HTTP_CLIENT_FACTORY_IMG)) { ImageLoaderClientFactory() }

  single<OkHttpClientFactory>(named(HTTP_CLIENT_FACTORY_API_CONVERSATIONS)) {
    ConversationsApiClientFactory(connectionStateInterceptor = get())
  }

  single<OkHttpClientFactory>(named(HTTP_CLIENT_FACTORY_API_VK)) {
    VkApiClientFactory(
      connectionStateInterceptor = get(),
      accessTokenInterceptor = get(),
      acceptLanguageInterceptor = get()
    )
  }
  single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }
  single<Converter.Factory> { GasConverterFactory() }
}