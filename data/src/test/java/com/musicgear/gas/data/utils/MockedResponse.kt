package com.musicgear.gas.data.utils

import com.musicgear.gas.data.remote.MockProtocolResponse
import okhttp3.mockwebserver.MockResponse

internal fun mockedResponse(init: MockProtocolResponse.() -> Unit): MockResponse {
  val builder = MockProtocolResponse()
  builder.init()
  return builder.build()
}