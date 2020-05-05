package com.musicgear.gas.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.musicgear.gas.data.local.db.LocalDateConverter
import org.threeten.bp.LocalDate

internal const val USER_DEFAULT_ID = 1

@Entity(tableName = "db_user")
@TypeConverters(LocalDateConverter::class)
internal class UserDB(
  @PrimaryKey val id: Int = USER_DEFAULT_ID,
  val firstName: String = "",
  val lastName: String = "",
  val email: String = "",
  val phone: String = "",
  val birthDate: LocalDate = LocalDate.MIN,
  val avatarUrl: String = ""
) {
  companion object {
    val EMPTY = UserDB()
  }
}