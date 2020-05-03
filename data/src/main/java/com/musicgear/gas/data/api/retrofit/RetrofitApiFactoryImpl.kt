package com.musicgear.gas.data.api.retrofit

import com.musicgear.gas.data.api.okhttp.OkHttpClientFactory
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

internal class RetrofitApiFactoryImpl(
  private val baseUrl: String,
  private val gasHttpClientFactory: OkHttpClientFactory,
  private val converterFactory: Converter.Factory,
  private val callAdapterFactory: CallAdapter.Factory
) {

  fun createRetrofitApi(): RetrofitApi = Retrofit.Builder()
    .client(gasHttpClientFactory.create())
    .baseUrl(baseUrl)
    .addCallAdapterFactory(callAdapterFactory)
    .addConverterFactory(converterFactory)
    .build()
    .create(RetrofitApi::class.java)
}