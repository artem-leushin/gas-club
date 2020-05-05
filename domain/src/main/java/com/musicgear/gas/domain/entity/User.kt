package com.musicgear.gas.domain.entity

import org.threeten.bp.LocalDate

data class User(
  val id: Int,
  val firstName: String,
  val lastName: String,
  val email: String,
  val phone: String,
  val birthDate: LocalDate,
  val avatarUrl: String
) {

  companion object {
    val EMPTY = User(
      id = -1,
      firstName = "",
      lastName = "",
      email = "",
      phone = "",
      birthDate = LocalDate.now(),
      avatarUrl = ""
    )
  }

  override fun toString(): String {
    return "$id,$email,$firstName,$firstName,$phone,${birthDate.toString()},$avatarUrl"
  }
}