package com.musicgear.gas.data.entity.remote

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import com.musicgear.gas.data.api.AttachmentTypeConverter
import com.musicgear.gas.domain.entity.AttachmentType

@JsonObject
internal data class PhotoCommentR(
  @JsonField(name = ["id"])
  var id: Int? = Int.MIN_VALUE,
  @JsonField(name = ["from_id"])
  var userId: Int? = Int.MIN_VALUE,
  @JsonField
  var text: String? = "",
  @JsonField
  var attachments: List<PhotoAttachmentR>? = null
)

@JsonObject
internal data class PhotoAttachmentR(
  @JsonField(typeConverter = AttachmentTypeConverter::class)
  var type: AttachmentType? = AttachmentType.UNKNOWN,
  @JsonField
  var photo: PhotoR? = null
)