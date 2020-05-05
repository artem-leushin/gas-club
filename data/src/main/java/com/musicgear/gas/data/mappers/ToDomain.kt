package com.musicgear.gas.data.mappers

import com.musicgear.gas.data.local.entity.UserDB
import com.musicgear.gas.data.local.entity.VkSessionDB
import com.musicgear.gas.domain.entity.User
import com.musicgear.gas.domain.entity.VkSession
import com.vk.api.sdk.auth.VKAccessToken

internal fun UserDB.toDomain() = User(
  id,
  firstName,
  lastName,
  email,
  phone,
  birthDate,
  avatarUrl
)

internal fun VKAccessToken.toDomain() = VkSession(
  userId,
  email ?: "",
  phone ?: "",
  accessToken
)

internal fun VkSessionDB.toDomain() = VkSession(
  userId,
  email,
  phone,
  accessToken
)