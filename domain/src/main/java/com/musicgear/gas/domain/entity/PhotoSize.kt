package com.musicgear.gas.domain.entity

data class Size(
  val srcUrl: String = "",
  val width: Int = Int.MIN_VALUE,
  val height: Int = Int.MIN_VALUE,
  val type: SizeType = SizeType.PROPORTIONAL_75
) {
  companion object {
    val EMPTY = Size()
  }
}

enum class SizeType(val code: String) {
  PROPORTIONAL_75("s"),
  PROPORTIONAL_130("m"),
  PROPORTIONAL_604("x"),
  RATIO_130("o"),
  RATIO_200("p"),
  RATIO_320("q"),
  RATIO_510("r"),
  PROPORTIONAL_807("y"),
  PROPORTIONAL_X1024("z"),
  PROPORTIONAL_X2560("w"),
  UNKNOWN("");

  companion object {
    fun valueFor(code: String): SizeType = values().firstOrNull { it.code == code } ?: UNKNOWN
  }
}