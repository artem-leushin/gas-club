package com.musicgear.gas.domain.entity

class Category(
  val id: Int,
  val name: String,
  val instruments: List<Instrument>
)

class Instrument(
  val id: Int,
  val name: String,
  val description: String,
  val photoUrl: String
)