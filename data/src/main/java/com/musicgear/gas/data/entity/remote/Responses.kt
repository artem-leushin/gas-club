package com.musicgear.gas.data.entity.remote

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
open class AlbumsResponse(
  @JsonField(name = ["count"]) var count: Int? = null,
  @JsonField(name = ["items"]) var items: List<AlbumR>? = null
)

@JsonObject
open class Response<T>(
  @JsonField(name = ["response"]) var response: T? = null
)