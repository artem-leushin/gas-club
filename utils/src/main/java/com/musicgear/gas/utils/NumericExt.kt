package com.musicgear.gas.utils

import android.content.res.Resources
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

val Float.dp: Float get() = this / Resources.getSystem().displayMetrics.density
val Float.px: Float get() = this * Resources.getSystem().displayMetrics.density

val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.dpF: Float get() = (this / Resources.getSystem().displayMetrics.density)
val Int.pxF: Float get() = (this * Resources.getSystem().displayMetrics.density)