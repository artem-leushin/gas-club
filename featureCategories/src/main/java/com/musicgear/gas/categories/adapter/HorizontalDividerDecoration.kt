package com.musicgear.gas.categories.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.withSave
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State
import com.musicgear.gas.categories.R
import com.musicgear.gas.utils.px
import com.musicgear.gas.utils.pxF
import com.musicgear.gas.utils.resolveAttribute

private const val DEFAULT_MARGIN_HORIZONTAL = 12f
private const val DEFAULT_DIVIDER_HEIGHT = 0.5f
private val DEFAULT_COLOR_ATTR = R.attr.colorPrimaryVariant

open class HorizontalDividerDecoration(
  context: Context,
  @AttrRes
  private val dividerColor: Int = DEFAULT_COLOR_ATTR,
  private val dividerHeight: Float = DEFAULT_DIVIDER_HEIGHT.px,
  private val horizontalMargin: Float = DEFAULT_MARGIN_HORIZONTAL
) : RecyclerView.ItemDecoration() {

  private val paint = Paint().apply {
    style = Paint.Style.FILL
    color = ContextCompat.getColor(context, context.resolveAttribute(dividerColor))
  }

  override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: State) {
    super.onDrawOver(canvas, parent, state)
    val childCount = parent.childCount

    if (childCount == 0) return

    for (i in 0 until childCount - 1) {
      val view = parent.getChildAt(i)
      canvas.withSave {
        canvas.drawRect(calculateDividerRectDimensions(view), paint)
      }
    }
  }

  private fun calculateDividerRectDimensions(view: View): RectF {
    val rect = RectF()
    val left = view.left + horizontalMargin + 100.pxF + 24.pxF
    val top = view.bottom - dividerHeight
    val right = view.right.toFloat() - horizontalMargin
    val bottom = view.bottom.toFloat()
    rect.set(left, top, right, bottom)
    return rect
  }
}