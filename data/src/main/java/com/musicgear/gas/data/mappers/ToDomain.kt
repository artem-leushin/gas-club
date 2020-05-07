package com.musicgear.gas.data.mappers

import com.musicgear.gas.data.entity.local.UserDB
import com.musicgear.gas.data.entity.local.VkSessionDB
import com.musicgear.gas.data.entity.remote.AlbumR
import com.musicgear.gas.data.entity.remote.CommentR
import com.musicgear.gas.data.entity.remote.PhotoR
import com.musicgear.gas.domain.entity.Category
import com.musicgear.gas.domain.entity.Comment
import com.musicgear.gas.domain.entity.Instrument
import com.musicgear.gas.domain.entity.User
import com.musicgear.gas.domain.entity.VkSession
import com.vk.api.sdk.auth.VKAccessToken
import org.threeten.bp.LocalDateTime

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

internal fun AlbumR.toDomain() = Category(
  id ?: Int.MIN_VALUE,
  title ?: "",
  description ?: "",
  photosCount ?: Int.MIN_VALUE,
  photoUrl ?: ""
)

internal fun PhotoR.toDomain() = Instrument(
  id ?: Int.MIN_VALUE,
  text ?: "",
  date ?: LocalDateTime.MIN,
  photoUrl ?: ""
)

internal fun CommentR.toDomain() = Comment(text ?: "")