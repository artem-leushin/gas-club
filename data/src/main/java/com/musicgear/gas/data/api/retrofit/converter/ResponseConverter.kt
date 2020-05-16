package com.musicgear.gas.data.api.retrofit.converter

import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.Type

internal abstract class BaseConverter<From, To> : Converter<From, To> {

  protected fun parseByLoganSquare(type: Type, value: String): Any? =
    LoganSquareJsonMapper.parse(type, value)

  protected fun serializeByLoganSquare(type: Type, value: Any): String =
    LoganSquareJsonMapper.serialize(type, value)
}

internal class ResponseConverter(
  private val type: Type
) : BaseConverter<ResponseBody, Any>() {

  override fun convert(responseBody: ResponseBody): Any? {
    val json = responseBody.string()
    responseBody.close()
    return parseByLoganSquare(type, json)
  }
}