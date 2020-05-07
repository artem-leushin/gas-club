package com.musicgear.gas.domain.entity

import org.threeten.bp.LocalDateTime

data class Category(
  val id: Int,
  val name: String,
  val description: String,
  val instrumentsCount: Int,
  val photoUrl: String
)

data class Instrument(
  val id: Int,
  val name: String,
  val dateAdded: LocalDateTime,
  val photoUrl: String
)

data class Comment(
val text: String
)