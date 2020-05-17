package com.musicgear.gas.data.api.retrofit.converter

import com.bluelinelabs.logansquare.ConverterUtils
import com.bluelinelabs.logansquare.LoganSquare
import com.musicgear.gas.data.entity.remote.ErrorResponse
import com.musicgear.gas.data.entity.remote.toException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Copied from original [LoganSquareResponseBodyConverter][com.github.aurae.retrofit2.LoganSquareResponseBodyConverter],
 * with additional error response parsing
 */
internal object LoganSquareJsonMapper {
  fun parse(type: Type, value: String): Any? {
    if (value.contains("error")) {
      val errorBody = LoganSquare.mapperFor(ErrorResponse::class.java).parse(value)
      throw errorBody.toException()
    }

    return when (type) {
      is Class<*> -> LoganSquare.parse(value, type) // Plain object conversion
      is ParameterizedType -> {
        val parameterizedType = type
        val typeArguments = parameterizedType.actualTypeArguments
        val firstType = typeArguments[0]

        when (parameterizedType.rawType) {
          is Map<*, *> -> LoganSquare.parseMap(value, typeArguments[1] as Class<*>)
          is List<*> -> LoganSquare.parseList(value, firstType as Class<*>)
          else -> LoganSquare.parse(value, ConverterUtils.parameterizedTypeOf(type)) // Generics
        }
      }
      else -> null
    }
  }

  fun serialize(type: Type, value: Any): String {
    val jsonString = if (type is ParameterizedType) {
      val rawType = type.rawType
      if (rawType !is List<*> && rawType !is Map<*, *>) {
        LoganSquare.serialize<Any>(value, ConverterUtils.parameterizedTypeOf(type))
      } else null
    } else LoganSquare.serialize(value)

    return jsonString!!
  }
}