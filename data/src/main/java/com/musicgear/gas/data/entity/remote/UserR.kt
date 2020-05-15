package com.musicgear.gas.data.entity.remote

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
data class UserR(
  @JsonField(name = ["id"])
  var id: Int? = null,
  @JsonField(name = ["first_name"])
  var firstName: String? = null,
  @JsonField(name = ["last_name"])
  var lastName: String? = null,
  @JsonField(name = ["screen_name"])
  var screenName: String? = null,
  @JsonField(name = ["photo_200_orig"])
  var photoUrl: String? = null
)