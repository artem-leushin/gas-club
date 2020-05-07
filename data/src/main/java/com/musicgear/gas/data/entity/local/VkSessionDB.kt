package com.musicgear.gas.data.entity.local

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