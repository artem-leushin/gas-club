package com.musicgear.gas.domain.entity

import org.threeten.bp.LocalDateTime

data class Category(
  val id: Int,
  val name: String,
  val description: String,
  val instrumentsCount: Int,
  val thumbUrl: String,
  val photoSizes: List<Size>
)

class Instrument(
  val id: Int,
  val name: String,
  val dateAdded: LocalDateTime,
  photoSizes: List<Size>
) {
  val photoSizes = photoSizes.sortedWith(Comparator { size1, size2 ->
    if (size1.type.ordinal > size2.type.ordinal) -1 else 1
  })
}

data class Comment(
  val text: String
)

