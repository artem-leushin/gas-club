package com.musicgear.gas.data.entity.remote

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import com.musicgear.gas.data.api.SizeTypeConverter
import com.musicgear.gas.domain.entity.SizeType

@JsonObject
internal data class AlbumR(
  @JsonField
  var id: Int? = Int.MIN_VALUE,
  @JsonField
  var title: String? = "",
  @JsonField
  var description: String? = "",
  @JsonField(name = ["size"])
  var photosCount: Int? = Int.MIN_VALUE,
  @JsonField(name = ["thumb_src"])
  var thumbUrl: String? = "",
  @JsonField(name = ["sizes"])
  var thumbSizes: List<SizeR>? = emptyList()
)

// TODO refactor to single sealed class instead of class with nested enum. Make type converter for it
@JsonObject
internal data class SizeR(
  @JsonField(name = ["src", "url"])
  var srcUrl: String? = "",
  @JsonField
  var width: Int? = 0,
  @JsonField
  var height: Int? = 0,
  @JsonField(name = ["type"], typeConverter = SizeTypeConverter::class)
  var type: SizeType? = SizeType.PROPORTIONAL_130
)