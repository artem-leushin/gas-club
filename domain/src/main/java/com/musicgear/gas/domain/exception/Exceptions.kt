package com.musicgear.gas.domain.exception

open class DomainException @JvmOverloads constructor(
  message: String? = null,
  sourceThrowable: Throwable? = null
) : RuntimeException(message, sourceThrowable)

class ConnectionErrorException : DomainException {
  constructor(message: String?) : super(message)
  constructor(sourceThrowable: Throwable?) : super(sourceThrowable?.message)
}

class VkLoginFailedException(code: Int?) :
  DomainException("Failed to login with VK. Error code: $code")

