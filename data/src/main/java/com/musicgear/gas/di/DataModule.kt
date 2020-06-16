package com.musicgear.gas.di

import com.musicgear.gas.data.BuildConfig
import com.musicgear.gas.data.api.okhttp.ClientFactory
import com.musicgear.gas.data.api.okhttp.ImageLoaderClientFactory
import com.musicgear.gas.data.api.okhttp.OkHttpClientFactory
import com.musicgear.gas.data.api.retrofit.RetrofitApiFactory
import com.musicgear.gas.data.api.retrofit.converter.GasConverterFactory
import com.musicgear.gas.data.database.room.GasRoomDbProvider
import com.musicgear.gas.data.datasource.CategoriesSource
import com.musicgear.gas.data.datasource.CommentsSource
import com.musicgear.gas.data.datasource.InstrumentsSource
import com.musicgear.gas.data.datasource.UserSource
import com.musicgear.gas.data.datasource.VkFacade
import com.musicgear.gas.data.datasource.VkSessionSource
import com.musicgear.gas.data.datasource.local.UserLocalSource
import com.musicgear.gas.data.datasource.local.VkSessionLocalSource
import com.musicgear.gas.data.datasource.remote.CategoriesRemoteSource
import com.musicgear.gas.data.datasource.remote.CommentsRemoteSource
import com.musicgear.gas.data.datasource.remote.InstrumentsRemoteSource
import com.musicgear.gas.data.datasource.remote.UserRemoteSource
import com.musicgear.gas.data.repository.CategoriesRepositoryImpl
import com.musicgear.gas.data.repository.CommentsRepositoryImpl
import com.musicgear.gas.data.repository.InstrumentsRepositoryImpl
import com.musicgear.gas.data.repository.ResourcesRepositoryImpl
import com.musicgear.gas.data.repository.UserRepositoryImpl
import com.musicgear.gas.data.service.AuthServiceImpl
import com.musicgear.gas.data.service.ConnectivityStreamFactory
import com.musicgear.gas.data.service.InternetObserverImpl
import com.musicgear.gas.data.service.SessionStatusServiceImpl
import com.musicgear.gas.data.service.VkFacadeImpl
import com.musicgear.gas.domain.constants.HTTP_CLIENT_FACTORY_API
import com.musicgear.gas.domain.constants.HTTP_CLIENT_FACTORY_IMG
import com.musicgear.gas.domain.constants.USER_SOURCE_LOCAL
import com.musicgear.gas.domain.constants.USER_SOURCE_REMOTE
import com.musicgear.gas.domain.repository.CategoriesRepository
import com.musicgear.gas.domain.repository.CommentsRepository
import com.musicgear.gas.domain.repository.InstrumentsRepository
import com.musicgear.gas.domain.repository.ResourcesRepository
import com.musicgear.gas.domain.repository.UserRepository
import com.musicgear.gas.domain.service.AuthService
import com.musicgear.gas.domain.service.InternetObserverService
import com.musicgear.gas.domain.service.SessionStatusService
import com.vk.api.sdk.VK
import okhttp3.HttpUrl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

val dataModule = module {
  single { BuildConfig.BASE_URL }
  single { HttpUrl.Builder() }

  single { VK }
  single<VkFacade> { VkFacadeImpl(vk = get()) }

  single { ConnectivityStreamFactory(androidApplication()) }
  single<InternetObserverService> { InternetObserverImpl(connectivityStreamFactory = get()) }
  single<ResourcesRepository> { ResourcesRepositoryImpl(resources = get()) }

  single<SessionStatusService> { SessionStatusServiceImpl(vkFacade = get()) }
  single<AuthService> { AuthServiceImpl(sessionSource = get(), vkFacade = get()) }
  single<VkSessionSource> { VkSessionLocalSource(dao = get()) }

  single<CategoriesSource> { CategoriesRemoteSource(api = get()) }
  single<InstrumentsSource> { InstrumentsRemoteSource(api = get()) }
  single<CommentsSource> { CommentsRemoteSource(api = get()) }
  single<UserSource>(named(USER_SOURCE_REMOTE)) { UserRemoteSource(api = get()) }
  single<UserSource>(named(USER_SOURCE_LOCAL)) { UserLocalSource(dao = get()) }

  single<UserRepository> {
    UserRepositoryImpl(
      local = get(named(USER_SOURCE_LOCAL)),
      remote = get(named(USER_SOURCE_REMOTE))
    )
  }
  single<CategoriesRepository> { CategoriesRepositoryImpl(remote = get()) }
  single<InstrumentsRepository> { InstrumentsRepositoryImpl(remote = get()) }
  single<CommentsRepository> { CommentsRepositoryImpl(remote = get()) }

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
    ClientFactory(
      connectivityService = get(),
      sessionSource = get()
    )
  }

  single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }
  single<Converter.Factory> { GasConverterFactory() }

}
