package com.musicgear.gas.utils

import android.os.Handler

fun delayAction(delay: Long = 150, action: () -> Unit) {
  Handler().postDelayed({ action() }, delay)
}