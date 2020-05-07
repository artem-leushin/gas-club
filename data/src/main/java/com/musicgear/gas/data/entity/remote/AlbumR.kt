package com.musicgear.gas.data.entity.remote

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
data class AlbumR(
  @JsonField
  var id: Int? = Int.MIN_VALUE,
  @JsonField
  var title: String? = "",
  @JsonField
  var description: String? = "",
  @JsonField(name = ["size"])
  var photosCount: Int? = Int.MIN_VALUE,
  @JsonField(name = ["thumb_src"])
  var photoUrl: String? = ""
)