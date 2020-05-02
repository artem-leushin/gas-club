package com.musicgear.gas.data.api.entity

import com.bluelinelabs.logansquare.LoganSquare
import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import com.musicgear.gas.domain.exception.DomainException

@JsonObject
internal class ErrorHolder(
  @JsonField(name = ["response_code"]) var responseCode: Int? = null,
  @JsonField(name = ["err_code"]) var errorCode: Int? = null,
  @JsonField(name = ["message"]) var message: String? = null
) {

  override fun toString(): String = LoganSquare.serialize(this)

  companion object {
    val noInternetConnection = ErrorHolder(
      responseCode = 503,
      message = "No internet connection. Service unavailable"
    ).toString()
  }
}

internal fun ErrorHolder.toException(): DomainException = DomainException(message ?: "")