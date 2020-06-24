package com.musicgear.gas.data.api.retrofit.converter

import com.bluelinelabs.logansquare.ConverterUtils
import com.bluelinelabs.logansquare.LoganSquare
import com.musicgear.gas.data.entity.remote.ErrorResponse
import com.musicgear.gas.data.entity.remote.toException
import com.musicgear.gas.domain.exception.DomainException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Copied from original [LoganSquareResponseBodyConverter][com.github.aurae.retrofit2.LoganSquareResponseBodyConverter],
 * with additional error response parsing
 */
internal object LoganSquareJsonMapper {
  private val parser = JsonParser()
  private val serializer = JsonSerializer()

  fun parse(type: Type, value: String): Any? = parser.parse(type, value)

  fun serialize(type: Type, value: Any): String = serializer.serialize(type, value)

  class JsonParser {
    fun parse(type: Type, value: String): Any? = when {
      value.contains("error") -> parseAndThrowDomainException(value)
      type is Class<*> -> parsePlainObject(value, type)
      type is ParameterizedType -> parseParameterizedType(type, value)
      else -> null
    }

    @Throws(DomainException::class)
    private fun parseAndThrowDomainException(value: String) {
      val errorBody = LoganSquare.mapperFor(ErrorResponse::class.java).parse(value)
      throw errorBody.toException()
    }

    private fun parsePlainObject(value: String, type: Class<*>): Any? = LoganSquare.parse(value, type)

    private fun parseParameterizedType(type: ParameterizedType, value: String): Any? {
      val typeArguments = type.actualTypeArguments
      val firstType = typeArguments[0]

      return when (type.rawType) {
        is Map<*, *> -> LoganSquare.parseMap(value, typeArguments[1] as Class<*>)
        is List<*> -> LoganSquare.parseList(value, firstType as Class<*>)
        else -> LoganSquare.parse(value, ConverterUtils.parameterizedTypeOf(type)) // Generics
      }
    }
  }

  class JsonSerializer {

    fun serialize(type: Type, value: Any): String = if (type is ParameterizedType)
      serializedParameterizedType(type, value)
    else LoganSquare.serialize(value)

    private fun serializedParameterizedType(type: ParameterizedType, value: Any): String {
      val rawType = type.rawType
      val typeIsCollection = rawType !is List<*> && rawType !is Map<*, *>

      return if (typeIsCollection) LoganSquare.serialize(value, ConverterUtils.parameterizedTypeOf(type))
      else "null"
    }
  }
}