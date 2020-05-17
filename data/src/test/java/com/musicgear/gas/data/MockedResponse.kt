package com.musicgear.gas.data

import okhttp3.mockwebserver.MockResponse

internal fun mockedResponse(init: MockProtocolResponse.() -> Unit): MockResponse {
  val builder = MockProtocolResponse()
  builder.init()
  return builder.build()
}