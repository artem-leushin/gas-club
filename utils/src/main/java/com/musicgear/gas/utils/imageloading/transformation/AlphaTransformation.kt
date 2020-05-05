package com.musicgear.gas.utils.imageloading.transformation

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Shader
import androidx.core.graphics.ColorUtils
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.musicgear.gas.utils.px
import java.security.MessageDigest

class AlphaTransformation : BitmapTransformation() {

  private val paint = Paint()
  private val endColor = Color.WHITE
  private val startColor = ColorUtils.setAlphaComponent(Color.WHITE, 0)

  override fun updateDiskCacheKey(messageDigest: MessageDigest) = messageDigest.update(ID_BYTES)

  override fun transform(
    pool: BitmapPool,
    toTransform: Bitmap,
    outWidth: Int,
    outHeight: Int
  ): Bitmap {
    val overlay = pool.get(toTransform.width, toTransform.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(overlay)
    canvas.drawBitmap(toTransform, 0f, 0f, null)

    val shader = LinearGradient(
      0f,
      0f,
      0f,
      toTransform.height.toFloat() * 2,
      startColor,
      endColor,
      Shader.TileMode.CLAMP
    )
    paint.shader = shader
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    canvas.drawRect(0f, 0f, toTransform.width.toFloat(), toTransform.height.toFloat(), paint)

    return overlay
  }

  override fun equals(other: Any?): Boolean = other is AlphaTransformation
  override fun hashCode(): Int = ID.hashCode()

  companion object {
    @JvmStatic
    private val ID = "com.musicgear.gas.utils.imageloading.transformation.AlphaTransformation"
    @JvmStatic
    private val ID_BYTES = ID.toByteArray(Key.CHARSET)
  }
}