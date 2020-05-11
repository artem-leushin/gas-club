package com.musicgear.gas.utils.imageloading.bindingadapter

import android.graphics.PorterDuff
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
  visibility = if (visible) View.VISIBLE
  else View.INVISIBLE
}

@BindingAdapter("navIconTint")
fun Toolbar.toolbarNavIconTintRes(@ColorRes tintColorRes: Int) {
  if (navigationIcon == null) return

  val tintColor = ContextCompat.getColor(context, tintColorRes)

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
    navigationIcon!!.setTint(tintColor)
  else
    navigationIcon!!.mutate().setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
}