package com.musicgear.gas.domain.entity

import android.content.Intent

data class VkSession(
  val userId: Int,
  val email: String,
  val phone: String,
  val accessToken: String
)

data class AuthBundle(
  val requestCode: Int,
  val resultCode: Int,
  val data: Intent?
)