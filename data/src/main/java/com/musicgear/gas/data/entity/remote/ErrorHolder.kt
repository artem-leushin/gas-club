package com.musicgear.gas.data.entity.remote

import com.bluelinelabs.logansquare.LoganSquare
import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import com.musicgear.gas.domain.exception.DomainException

@JsonObject
internal class ErrorResponse(
  @JsonField(name = ["error"]) var error: ErrorHolder? = null
)

@JsonObject
internal class ErrorHolder(
  @JsonField(name = ["error_code"]) var errorCode: Int? = null,
  @JsonField(name = ["error_msg"]) var message: String? = null
) {

  override fun toString(): String = LoganSquare.serialize(this)

  companion object {
    val noInternetConnection = ErrorHolder(
      errorCode = 503,
      message = "No internet connection. Service unavailable"
    ).toString()
  }
}

internal fun ErrorHolder.toException(): DomainException = DomainException(message ?: "")
internal fun ErrorResponse.toException(): DomainException = DomainException(error?.message ?: "")