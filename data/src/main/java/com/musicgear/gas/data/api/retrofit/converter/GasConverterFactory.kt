package com.musicgear.gas.data.api.retrofit.converter

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
internal class GasConverterFactory : Factory() {

  override fun responseBodyConverter(
    type: Type,
    annotations: Array<Annotation>,
    retrofit: Retrofit
  ): Converter<ResponseBody, *> = ResponseConverter(type)
}

