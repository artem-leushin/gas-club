package com.musicgear.gas.data.api

import com.bluelinelabs.logansquare.typeconverters.IntBasedTypeConverter
import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter
import com.musicgear.gas.domain.entity.SizeType
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

internal class LocalDateStringConverter : StringBasedTypeConverter<LocalDate>() {
  override fun convertToString(date: LocalDate?): String {
    val localDate = date ?: LocalDate.now()
    val localDateTime = LocalDate.of(localDate.year, localDate.month, localDate.dayOfMonth)
    return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
  }

  override fun getFromString(date: String?): LocalDate =
    LocalDate.parse(date ?: "", DateTimeFormatter.ISO_DATE_TIME)
}

internal class LocalDateTimeStringConverter : StringBasedTypeConverter<LocalDateTime>() {
  override fun convertToString(date: LocalDateTime?): String {
    val localDate = date ?: LocalDateTime.now()
    return localDate.format(DateTimeFormatter.ISO_DATE_TIME)
  }

  override fun getFromString(date: String?): LocalDateTime = try {
    LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
  } catch (e: Exception) {
    LocalDateTime.MIN
  }
}

internal class LocalDateUnixConverter : IntBasedTypeConverter<LocalDate>() {
  override fun convertToInt(date: LocalDate?): Int {
    val localDate = date ?: LocalDate.now()
    val localDateTime = LocalDate.of(localDate.year, localDate.month, localDate.dayOfMonth)
    return localDateTime.toEpochDay().toInt()
  }

  override fun getFromInt(date: Int): LocalDate = LocalDate.ofEpochDay(date.toLong())
}

internal class LocalDateTimeUnixConverter : IntBasedTypeConverter<LocalDateTime>() {
  override fun convertToInt(date: LocalDateTime?): Int {
    val localDate = date ?: LocalDateTime.now()
    return localDate.toEpochSecond(ZoneOffset.UTC).toInt()
  }

  override fun getFromInt(date: Int): LocalDateTime = try {
    LocalDateTime.ofEpochSecond(date.toLong(), 0, ZoneOffset.UTC)
  } catch (e: Exception) {
    LocalDateTime.MIN
  }
}

class SizeTypeConverter : StringBasedTypeConverter<SizeType>() {
  override fun convertToString(`object`: SizeType?): String = `object`?.code ?: ""

  override fun getFromString(code: String?): SizeType = SizeType.valueFor(code ?: "s")
}