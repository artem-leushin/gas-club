package com.musicgear.gas.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_vk_session")
data class VkSessionDB(
  @PrimaryKey
  val userId: Int,
  val email: String,
  val phone: String,
  val accessToken: String
)