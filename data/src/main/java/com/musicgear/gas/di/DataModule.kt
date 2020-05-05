package com.musicgear.gas.di

import com.github.aurae.retrofit2.LoganSquareConverterFactory
import com.musicgear.gas.data.datasource.local.UserLocalSource
import com.musicgear.gas.data.datasource.local.VkSessionLocalSource
import com.musicgear.gas.data.datasource.remote.UserRemoteSource
import com.musicgear.gas.data.local.db.GasRoomDbProvider
import com.musicgear.gas.data.remote.api.okhttp.ClientFactory
import com.musicgear.gas.data.remote.api.okhttp.ImageLoaderClientFactory
import com.musicgear.gas.data.remote.api.okhttp.OkHttpClientFactory
import com.musicgear.gas.data.remote.api.okhttp.RxErrorHandlingCallAdapterFactory
import com.musicgear.gas.data.remote.api.retrofit.RetrofitApiFactory
import com.musicgear.gas.data.repository.UserRepositoryImpl
import com.musicgear.gas.data.service.AuthServiceImpl
import com.musicgear.gas.data.service.InternetObserverImpl
import com.musicgear.gas.data.service.SessionStatusServiceImpl
import com.musicgear.gas.domain.HTTP_CLIENT_FACTORY_API
import com.musicgear.gas.domain.HTTP_CLIENT_FACTORY_IMG
import com.musicgear.gas.domain.USER_SOURCE_LOCAL
import com.musicgear.gas.domain.USER_SOURCE_REMOTE
import com.musicgear.gas.domain.datasource.UserSource
import com.musicgear.gas.domain.datasource.VkSessionSource
import com.musicgear.gas.domain.repository.UserRepository
import com.musicgear.gas.domain.service.AuthService
import com.musicgear.gas.domain.service.InternetObserverService
import com.musicgear.gas.domain.service.SessionStatusService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter

val dataModule = module {
  single<InternetObserverService> { InternetObserverImpl(context = get()) }

  single<SessionStatusService> { SessionStatusServiceImpl() }
  single<AuthService> { AuthServiceImpl(sessionSource = get()) }
  single<VkSessionSource> { VkSessionLocalSource(dao = get()) }
  single<UserRepository> {
    UserRepositoryImpl(
      local = get(named(USER_SOURCE_LOCAL)),
      remote = get(named(USER_SOURCE_REMOTE))
    )
  }
  single<UserSource>(named(USER_SOURCE_REMOTE)) { UserRemoteSource(api = get()) }
  single<UserSource>(named(USER_SOURCE_LOCAL)) { UserLocalSource(dao = get()) }

  single { GasRoomDbProvider.createGasRoomDb(get()).userDao() }
  single { GasRoomDbProvider.createGasRoomDb(get()).vkSessionDao() }

  single {
    RetrofitApiFactory(
      baseUrl = get(),
      gasHttpClientFactory = get(named(HTTP_CLIENT_FACTORY_API)),
      converterFactory = get(),
      callAdapterFactory = get()
    ).createRetrofitApi()
  }

  single<OkHttpClientFactory>(named(HTTP_CLIENT_FACTORY_IMG)) { ImageLoaderClientFactory() }
  single<OkHttpClientFactory>(named(HTTP_CLIENT_FACTORY_API)) {
    ClientFactory(connectivityService = get())
  }

  single<CallAdapter.Factory> { RxErrorHandlingCallAdapterFactory() }
  single<Converter.Factory> { LoganSquareConverterFactory.create() }
}
