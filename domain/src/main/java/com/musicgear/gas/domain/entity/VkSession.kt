package com.musicgear.gas.domain.entity

import android.content.Intent

data class VkSession(
  val userId: Int = Int.MIN_VALUE,
  val email: String = "",
  val phone: String = "",
  val accessToken: String = ""
) {
  companion object {
    val EMPTY = VkSession()
  }

  fun isEmpty() = this == EMPTY
}

data class AuthBundle(
  val requestCode: Int,
  val resultCode: Int,
  val data: Intent?
)