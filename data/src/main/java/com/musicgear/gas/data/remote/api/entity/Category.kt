package com.musicgear.gas.data.remote.api.entity


class CategoryR(
  val id: Int,
  val name: String,
  val instruments: List<InstrumentR>
)

class InstrumentR(
  val id: Int,
  val name: String,
  val description: String,
  val photoUrl: String
)