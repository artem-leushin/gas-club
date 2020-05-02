package com.musicgear.gas.data.api.okhttp

import com.bluelinelabs.logansquare.LoganSquare
import com.musicgear.gas.data.api.entity.ErrorHolder
import okhttp3.ResponseBody
import retrofit2.Converter

internal class ErrorResponseConverter : Converter<ResponseBody, Any> {

  override fun convert(responseBody: ResponseBody): Any? {
    return LoganSquare.mapperFor(ErrorHolder::class.java).parse(responseBody.byteStream())
  }
}