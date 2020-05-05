package com.musicgear.gas.data.mappers

import com.musicgear.gas.data.local.entity.UserDB
import com.musicgear.gas.data.local.entity.VkSessionDB
import com.musicgear.gas.domain.entity.User
import com.musicgear.gas.domain.entity.VkSession

internal fun User.toDB() = UserDB(
  id,
  firstName,
  lastName,
  email,
  phone,
  birthDate,
  avatarUrl
)

internal fun VkSession.toDB() = VkSessionDB(
  userId,
  email,
  phone,
  accessToken
)