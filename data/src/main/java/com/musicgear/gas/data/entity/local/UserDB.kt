package com.musicgear.gas.data.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.musicgear.gas.data.database.LocalDateConverter

internal const val USER_DEFAULT_ID = 1

@Entity(tableName = "db_user")
@TypeConverters(LocalDateConverter::class)
internal class UserDB(
  @PrimaryKey val id: Int = USER_DEFAULT_ID,
  val firstName: String = "",
  val lastName: String = "",
  val screenName: String = "",
  val avatarUrl: String = ""
) {
  companion object {
    val EMPTY = UserDB()
  }
}