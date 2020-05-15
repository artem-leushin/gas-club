package com.musicgear.gas.data.entity.remote

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import com.musicgear.gas.data.api.LocalDateTimeUnixConverter
import org.threeten.bp.LocalDateTime

@JsonObject
internal data class PhotoR(
  @JsonField
  var id: Int? = Int.MIN_VALUE,
  @JsonField(name = ["album_id"])
  var albumId: Int? = Int.MIN_VALUE,
  @JsonField(name = ["user_id"])
  var userId: Int? = Int.MIN_VALUE,
  @JsonField
  var text: String? = "",
  @JsonField(typeConverter = LocalDateTimeUnixConverter::class)
  var date: LocalDateTime? = LocalDateTime.MIN,
  @JsonField(name = ["sizes"])
  var photoSizes: List<SizeR>? = emptyList()
)