package com.musicgear.gas.data.utils

import com.musicgear.gas.data.api.retrofit.converter.LoganSquareJsonMapper
import okhttp3.mockwebserver.MockResponse

internal inline fun mockStringResponse(init: StringMockResponse.() -> Unit): MockResponse {
  val builder = StringMockResponse()
  builder.init()
  return builder.build()
}

internal inline fun mockObjectResponse(init: ObjectMockResponse.() -> Unit): MockResponse {
  val builder = ObjectMockResponse()
  builder.init()
  return builder.build()
}

internal class ObjectMockResponse : StringMockResponse() {
  var responseBody: Any? = null

  override fun build(): MockResponse {
    checkNotNull(responseBody)
    responseBodyString = LoganSquareJsonMapper.serialize(responseBody!!.javaClass, responseBody!!)
    return super.build()
  }
}

internal open class StringMockResponse {
  var serverResponseCode: Int? = null
  var responseBodyString: String? = null

  open fun build(): MockResponse = MockResponse().apply {
    setResponseCode(checkNotNull(serverResponseCode))
    setBody(checkNotNull(responseBodyString))
  }
}
