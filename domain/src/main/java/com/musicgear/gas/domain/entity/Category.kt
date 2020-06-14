package com.musicgear.gas.domain.entity

import org.threeten.bp.LocalDateTime

data class Category(
  val id: Int = Int.MIN_VALUE,
  val name: String = "",
  val description: String = "",
  val instrumentsCount: Int = Int.MIN_VALUE,
  val thumbUrl: String = "",
  val photoSizes: List<Size> = emptyList()
)

data class InstrumentPhoto(
  val id: Int = Int.MIN_VALUE,
  val albumId: Int = Int.MIN_VALUE,
  val userId: Int = Int.MIN_VALUE,
  val name: String = "",
  val dateAdded: LocalDateTime = LocalDateTime.MIN,
  private val sizes: List<Size> = emptyList()
) {
  val photoSizes = sizes.sortedWith(Comparator { size1, size2 ->
    if (size1.type.ordinal > size2.type.ordinal) -1 else 1
  })

  companion object {
    val EMPTY = InstrumentPhoto()
  }
}

data class Comment(
  val id: Int = Int.MIN_VALUE,
  val userId: Int = Int.MIN_VALUE,
  val text: String = "",
  val attachments: List<PhotoAttachment> = emptyList()
)

data class PhotoAttachment(
  val type: AttachmentType = AttachmentType.UNKNOWN,
  val photo: InstrumentPhoto = InstrumentPhoto.EMPTY
)

data class InstrumentDetails(
  private val userComments: List<Comment> = emptyList()
) {
  val text = buildString {
    userComments.forEach { appendln(it.text) }
  }

  companion object {
    val EMPTY = InstrumentDetails(emptyList())
  }
}