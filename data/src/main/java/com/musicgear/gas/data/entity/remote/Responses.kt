package com.musicgear.gas.data.entity.remote

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
internal open class UserResponse(
  @JsonField(name = ["response"]) var user: List<UserR>? = null
)


@JsonObject
internal open class AlbumsResponse(
  @JsonField(name = ["count"]) var count: Int? = null,
  @JsonField(name = ["items"]) var items: List<AlbumR>? = null
)

@JsonObject
internal open class PhotosResponse(
  @JsonField(name = ["count"]) var count: Int? = null,
  @JsonField(name = ["items"]) var items: List<PhotoR>? = null
)

@JsonObject
internal open class CommentsResponse(
  @JsonField(name = ["count"]) var count: Int? = null,
  @JsonField(name = ["items"]) var items: List<CommentR>? = null
)

@JsonObject
internal open class Response<T>(
  @JsonField(name = ["response"]) var response: T? = null
)