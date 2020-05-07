package com.musicgear.gas.categories.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State

class MarginItemDecoration(
  private val left: Int = 0,
  private val top: Int = 0,
  private val right: Int = 0,
  private val bottom: Int = 0
) : ItemDecoration() {

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
    val lastItemPosition = parent.adapter!!.itemCount - 1
    val viewAdapterPosition = parent.findContainingViewHolder(view)?.adapterPosition ?: -1

    when (viewAdapterPosition) {
      0 -> outRect.set(left, top, right, bottom)
      lastItemPosition -> outRect.set(left, top, right, bottom)
      else -> outRect.set(left, top, right, bottom)
    }
  }
}