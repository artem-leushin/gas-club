package com.musicgear.gas.data.local.db

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

internal class LocalDateConverter {
  private val serverFormat = DateTimeFormatter.ISO_DATE_TIME

  @TypeConverter
  fun fromStringToDate(dateString: String): LocalDate {
    return LocalDate.parse(dateString, serverFormat)
  }

  @TypeConverter
  fun fromDateToString(date: LocalDate): String {
    return date.atStartOfDay().format(serverFormat)
  }
}

internal class LocalDateTimeConverter {
  private val serverFormat = DateTimeFormatter.ISO_DATE_TIME

  @TypeConverter
  fun fromStringToDate(dateString: String): LocalDateTime {
    return LocalDateTime.parse(dateString, serverFormat)
  }

  @TypeConverter
  fun fromDateToString(date: LocalDateTime): String {
    return date.format(serverFormat)
  }
}
