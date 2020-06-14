package com.musicgear.gas.domain.entity

data class User(
  val id: Int = Int.MIN_VALUE,
  val firstName: String = "",
  val lastName: String = "",
  val screenName: String = "",
  val avatarUrl: String = ""
) {

  val fullName: String = "$firstName $lastName"

  companion object {
    val EMPTY = User(
      id = -1,
      firstName = "",
      lastName = "",
      screenName = "",
      avatarUrl = ""
    )
  }

  fun isEmpty() = this == EMPTY

  override fun toString(): String {
    return "$id,,$firstName,$firstName,$avatarUrl"
  }
}