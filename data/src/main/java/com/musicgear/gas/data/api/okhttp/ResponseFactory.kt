package com.musicgear.gas.data.api.okhttp

import com.musicgear.gas.data.entity.remote.ErrorHolder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

internal interface HttpResponseFactory {
  fun createErrorResponse(request: Request): Response
  fun createSuccessResponse(request: Request): Response
}

internal object ResponseFactory : HttpResponseFactory {

  private const val mimeJson = "application/json"

  override fun createErrorResponse(request: Request): Response {
    val errorBody = ErrorHolder.noInternetConnection

    return Response.Builder()
      .request(request)
      .code(errorBody.errorCode!!)
      .message(errorBody.message!!)
      .protocol(Protocol.HTTP_1_1)
      .body(errorBody.toString().toResponseBody(mimeJson.toMediaTypeOrNull()))
      .build()
  }

  override fun createSuccessResponse(request: Request): Response = Response.Builder()
    .request(request)
    .code(200)
    .message("Ok")
    .protocol(Protocol.HTTP_1_1)
    .body("".toResponseBody(mimeJson.toMediaTypeOrNull()))
    .build()
}