package com.musicgear.gas.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes

fun Activity.isOnScreen(view: View): Boolean {
  if (view.isShown.not()) {
    return false
  }
  val actualPosition = Rect()
  view.getGlobalVisibleRect(actualPosition)
  val screen = Rect(0, 0, screenWidth(), screenHeight())
  return actualPosition.intersect(screen)
}

fun screenWidth() = Resources.getSystem().displayMetrics.widthPixels
fun screenHeight() = Resources.getSystem().displayMetrics.heightPixels

fun Context.resolveAttribute(@AttrRes attr: Int): Int {
  val value = TypedValue()
  theme.resolveAttribute(attr, value, true)
  return value.resourceId
}