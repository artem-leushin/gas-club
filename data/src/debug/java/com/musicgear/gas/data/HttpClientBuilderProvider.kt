package com.musicgear.gas.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class HttpClientBuilderProvider {

  companion object {
    fun create(): OkHttpClient.Builder = OkHttpClient.Builder()
      .connectTimeout(15, TimeUnit.SECONDS)
      .writeTimeout(15, TimeUnit.SECONDS)
      .readTimeout(15, TimeUnit.SECONDS)
      .addInterceptor(loggingInterceptor())
  }
}

private fun loggingInterceptor(): HttpLoggingInterceptor {
  val loggingInterceptor = HttpLoggingInterceptor()
  loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
  return loggingInterceptor
}