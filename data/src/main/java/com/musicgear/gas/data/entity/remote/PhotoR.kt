package com.musicgear.gas.data.entity.remote

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import com.musicgear.gas.data.api.LocalDateTimeUnixConverter
import org.threeten.bp.LocalDateTime

@JsonObject
data class PhotoR(
  @JsonField
  var id: Int? = Int.MIN_VALUE,
  @JsonField
  var text: String? = "",
  @JsonField(typeConverter = LocalDateTimeUnixConverter::class)
  var date: LocalDateTime? = LocalDateTime.MIN,
  @JsonField(name = ["photo_1280"])
  var photoUrl: String? = ""
)