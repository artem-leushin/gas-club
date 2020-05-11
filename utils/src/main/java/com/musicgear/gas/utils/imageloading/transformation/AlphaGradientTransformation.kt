package com.musicgear.gas.utils.imageloading.transformation

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Shader
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class AlphaGradientTransformation(
  @ColorInt gradientColor: Int = Color.WHITE,
  private val opacityEdge: Int = Gravity.BOTTOM,
  private val borderPosition: Float = 1f,
  private val endPointOffsetBy: Float = 2f
) : BitmapTransformation() {

  private val paint = Paint()
  private val endColor = gradientColor
  private val startColor = ColorUtils.setAlphaComponent(endColor, 0)

  override fun updateDiskCacheKey(messageDigest: MessageDigest) = messageDigest.update(ID_BYTES)

  override fun transform(
    pool: BitmapPool,
    toTransform: Bitmap,
    outWidth: Int,
    outHeight: Int
  ): Bitmap {
    val overlay = pool.get(toTransform.width, toTransform.height, Bitmap.Config.ARGB_8888)
    paint.shader = createGradient(opacityEdge, toTransform)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    val canvas = Canvas(overlay)
    canvas.drawBitmap(toTransform, 0f, 0f, null)
    canvas.drawRect(0f, 0f, toTransform.width.toFloat(), toTransform.height.toFloat(), paint)

    return overlay
  }

  private fun createGradient(opacityDirection: Int, toTransform: Bitmap): LinearGradient {
    val start = PointF()
    val end = PointF()
    val verticalEnd = toTransform.height.toFloat() * endPointOffsetBy
    val horizontalEnd = toTransform.width.toFloat() * endPointOffsetBy

    when (opacityDirection) {
      Gravity.BOTTOM -> {
        start.set(0f, 0f)
        end.set(0f, verticalEnd)
      }
      Gravity.TOP -> {
        start.set(0f, verticalEnd)
        end.set(0f, 0f)
      }
      Gravity.START -> {
        start.set(horizontalEnd, 0f)
        end.set(0f, 0f)
      }
      Gravity.END -> {
        start.set(0f, horizontalEnd)
        end.set(0f, 0f)
      }
    }

    return LinearGradient(
      start.x,
      start.y,
      end.x,
      end.y,
      intArrayOf(startColor, endColor),
      floatArrayOf(0f, borderPosition),
      Shader.TileMode.CLAMP
    )
  }

  override fun equals(other: Any?): Boolean = other is AlphaGradientTransformation
  override fun hashCode(): Int = ID.hashCode()

  companion object {
    @JvmStatic
    private val ID = "com.musicgear.gas.utils.imageloading.transformation.AlphaTransformation"
    @JvmStatic
    private val ID_BYTES = ID.toByteArray(Key.CHARSET)
  }
}