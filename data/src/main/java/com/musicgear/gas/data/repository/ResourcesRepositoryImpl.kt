package com.musicgear.gas.data.repository

import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat
import com.musicgear.gas.domain.repository.ResourcesRepository

class ResourcesRepositoryImpl(private val resources: Resources) : ResourcesRepository {
  override fun getText(stringRes: Int): CharSequence = resources.getText(stringRes)
  override fun getString(stringRes: Int): String = resources.getString(stringRes)
  override fun getQuantityString(stringRes: Int, quantity: Int): String = resources.getString(stringRes)
  override fun getTextArray(arrayRes: Int): Array<CharSequence> = resources.getTextArray(arrayRes)
  override fun getStringArray(arrayRes: Int): Array<String> = resources.getStringArray(arrayRes)
  override fun getIntArray(arrayRes: Int): IntArray = resources.getIntArray(arrayRes)
  override fun getDimension(dimenRes: Int): Float = resources.getDimension(dimenRes)
  override fun getColor(colorRes: Int): Int = ResourcesCompat.getColor(resources, colorRes, null)
  override fun getBoolean(boolRes: Int): Boolean = resources.getBoolean(boolRes)
  override fun getInteger(integerRes: Int): Int = resources.getInteger(integerRes)
}
