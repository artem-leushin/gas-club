package com.musicgear.gas.data.api.retrofit.converter

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class GasConverterFactory : Factory() {

  override fun responseBodyConverter(
    type: Type,
    annotations: Array<Annotation>,
    retrofit: Retrofit
  ): Converter<ResponseBody, *> = ResponseConverter(type)
}

internal class ResponseConverter(private val type: Type) :
  Converter<ResponseBody, Any> {

  override fun convert(responseBody: ResponseBody): Any? {
    val json = responseBody.string()
    responseBody.close()
    return parseByLoganSquare(type, json)
  }

  private fun parseByLoganSquare(type: Type, value: String): Any? =
    LoganSquareJsonMapper.parse(type, value)

  private fun serializeByLoganSquare(type: Type, value: Any): String =
    LoganSquareJsonMapper.serialize(
      type,
      value
    )
}