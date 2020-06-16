package com.musicgear.gas.data.mappers

import com.musicgear.gas.data.entity.local.UserDB
import com.musicgear.gas.data.entity.local.VkSessionDB
import com.musicgear.gas.data.entity.remote.AlbumR
import com.musicgear.gas.data.entity.remote.CommentR
import com.musicgear.gas.data.entity.remote.PhotoAttachmentR
import com.musicgear.gas.data.entity.remote.PhotoR
import com.musicgear.gas.data.entity.remote.SizeR
import com.musicgear.gas.data.entity.remote.UserR
import com.musicgear.gas.domain.entity.AttachmentType
import com.musicgear.gas.domain.entity.Category
import com.musicgear.gas.domain.entity.Comment
import com.musicgear.gas.domain.entity.InstrumentPhoto
import com.musicgear.gas.domain.entity.PhotoAttachment
import com.musicgear.gas.domain.entity.Size
import com.musicgear.gas.domain.entity.SizeType
import com.musicgear.gas.domain.entity.User
import com.musicgear.gas.domain.entity.VkSession
import com.vk.api.sdk.auth.VKAccessToken
import org.threeten.bp.LocalDateTime

internal fun UserDB.toDomain() = User(
  id,
  firstName,
  lastName,
  screenName,
  avatarUrl
)
internal fun UserR.toDomain() = User(
  id ?: Int.MIN_VALUE,
  firstName ?: "",
  lastName ?: "",
  screenName ?: "",
  photoUrl ?: ""
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
  thumbUrl ?: "",
  thumbSizes?.map { it.toDomain() } ?: listOf()
)

internal fun PhotoR.toDomain() = InstrumentPhoto(
  id ?: Int.MIN_VALUE,
  albumId ?: Int.MIN_VALUE,
  userId ?: Int.MIN_VALUE,
  text ?: "",
  date ?: LocalDateTime.MIN,
  photoSizes?.map { it.toDomain() } ?: listOf()
)

internal fun CommentR.toDomain() = Comment(
  id ?: Int.MIN_VALUE,
  userId ?: Int.MIN_VALUE,
  text ?: "",
  attachments?.map { it.toDomain() } ?: emptyList()
)

internal fun PhotoAttachmentR.toDomain() = PhotoAttachment(
  type ?: AttachmentType.UNKNOWN,
  photo?.toDomain() ?: InstrumentPhoto.EMPTY
)

internal fun SizeR.toDomain() = Size(
  srcUrl ?: "",
  width ?: Int.MIN_VALUE,
  height ?: Int.MIN_VALUE,
  type ?: SizeType.PROPORTIONAL_75
)