package com.musicgear.gas.login.matchers

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.musicgear.gas.utils.getColorByAttr
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

fun withDrawable(
  @DrawableRes id: Int,
  @ColorRes tint: Int? = null,
  tintMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN
) = object : TypeSafeMatcher<View>() {
  override fun describeTo(description: Description) {
    description.appendText("ImageView with drawable same as drawable with id $id")
    tint?.let { description.appendText(", tint color id: $tint, mode: $tintMode") }
  }

  override fun matchesSafely(view: View): Boolean {
    val context = view.context
    val tintColor = tint?.toColor(context)
    val expectedBitmap = context.getDrawable(id)?.tinted(tintColor, tintMode)?.toBitmap()

    return view is ImageView && view.drawable.toBitmap().sameAs(expectedBitmap)
  }
}

fun withAsset(assetName: String) = object : TypeSafeMatcher<View>() {
  override fun describeTo(description: Description) {
    description.appendText("ImageView with drawable same as drawable with assetName $assetName")
  }

  override fun matchesSafely(view: View): Boolean {
    val expectedBitmap = view.context.getAssetDrawable(assetName).toBitmap()
    return view is ImageView && view.drawable.toBitmap().sameAs(expectedBitmap)
  }
}

private fun Drawable.tinted(
  @ColorInt tintColor: Int? = null,
  tintMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN
) = apply {
  setTintList(tintColor?.toColorStateList())
  setTintMode(tintMode)
}

private fun Context.getAssetDrawable(assetName: String): Drawable =
  Drawable.createFromResourceStream(resources, TypedValue(), assets.open(assetName), null)

private fun Int.toColorStateList() = ColorStateList.valueOf(this)
private fun Int.toColor(context: Context) = ContextCompat.getColor(context, this)

fun hasDrawable() = object : TypeSafeMatcher<View>() {
  override fun describeTo(description: Description) {
    description.appendText("ImageView has drawable as image resource")
  }

  override fun matchesSafely(view: View): Boolean = (view as ImageView).drawable != null
}

fun hasBackgroundColor(@AttrRes color: Int) = object : TypeSafeMatcher<View>() {
  override fun describeTo(description: Description) {
    description.appendText("View has color $color as background")
  }

  override fun matchesSafely(view: View): Boolean {
    val validColor = ColorDrawable(view.context.getColorByAttr(color))
    val testedColor = view.background as ColorDrawable
    return validColor == testedColor
  }
}
