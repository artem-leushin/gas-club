package com.musicgear.gas.domain.exception

class ConnectionErrorException : DomainException {
  constructor(message: String?) : super(message)
  constructor(sourceThrowable: Throwable?) : super(sourceThrowable?.message)
}

open class DomainException : RuntimeException {
  constructor() : super()
  constructor(message: String?) : super(message)
  constructor(message: String?, sourceThrowable: Throwable?) : super(message, sourceThrowable)
  constructor(sourceThrowable: Throwable?) : super(sourceThrowable)
}