package com.musicgear.gas.domain.repository

interface ResourcesRepository {
  fun getText(stringRes: Int): CharSequence
  fun getString(stringRes: Int): String
  fun getQuantityString(stringRes: Int, quantity: Int): String
  fun getTextArray(arrayRes: Int): Array<CharSequence>
  fun getStringArray(arrayRes: Int): Array<String>
  fun getIntArray(arrayRes: Int): IntArray
  fun getDimension(dimenRes: Int): Float
  fun getColor(colorRes: Int): Int
  fun getBoolean(boolRes: Int): Boolean
  fun getInteger(integerRes: Int): Int
}
