package com.musicgear.gas.data.mappers

import com.musicgear.gas.data.entity.local.UserDB
import com.musicgear.gas.data.entity.local.VkSessionDB
import com.musicgear.gas.domain.entity.User
import com.musicgear.gas.domain.entity.VkSession

internal fun User.toDB() = UserDB(
  id,
  firstName,
  lastName,
  screenName,
  avatarUrl
)

internal fun VkSession.toDB() = VkSessionDB(
  userId,
  email,
  phone,
  accessToken
)