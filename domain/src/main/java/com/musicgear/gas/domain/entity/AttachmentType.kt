package com.musicgear.gas.domain.entity

enum class AttachmentType(val code: String) {
  PHOTO("photo"),
  VIDEO("video"),
  AUDIO("audio"),
  DOC("document"),
  UNKNOWN("");

  companion object {
    fun valueFor(code: String) = AttachmentType.values().firstOrNull { code == it.code } ?: UNKNOWN
  }
}