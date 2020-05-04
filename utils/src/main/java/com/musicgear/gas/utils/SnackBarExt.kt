package com.musicgear.gas.utils

import android.app.Activity
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

// VIEW
fun View.snackBarLong(message: String) {
  val snack = Snackbar.make(
    this,
    message,
    Snackbar.LENGTH_LONG
  )
  snack.show()
}

// ACTIVITY
fun Activity.snackBarShort(message: CharSequence?): Snackbar? =
  snackBar(message, Snackbar.LENGTH_SHORT)

fun Activity.snackBarShort(@StringRes stringRes: Int): Snackbar? =
  snackBar(getString(stringRes), Snackbar.LENGTH_LONG)

fun Activity.snackBarLong(message: CharSequence?): Snackbar? =
  snackBar(message, Snackbar.LENGTH_LONG)

fun Activity.snackBarLong(@StringRes stringRes: Int): Snackbar? =
  snackBar(getString(stringRes), Snackbar.LENGTH_LONG)

private fun Activity.snackBar(message: CharSequence?, duration: Int): Snackbar? =
  message?.let { window.decorView.run { Snackbar.make(this, message, duration) } }


// FRAGMENT
fun Fragment.snackBarShort(message: CharSequence?): Snackbar? =
  message?.let { snackBar(message, Snackbar.LENGTH_SHORT) }

fun Fragment.snackBarShort(@StringRes stringRes: Int): Snackbar? =
  snackBar(getString(stringRes), Snackbar.LENGTH_SHORT)

fun Fragment.snackBarLong(message: CharSequence?): Snackbar? =
  message?.let { snackBar(message, Snackbar.LENGTH_LONG) }

fun Fragment.snackBarLong(@StringRes stringRes: Int): Snackbar? =
  snackBar(getString(stringRes), Snackbar.LENGTH_LONG)

private fun Fragment.snackBar(message: CharSequence, duration: Int): Snackbar? =
  view?.let { Snackbar.make(it, message, duration) }

// FRAGMENT GLOBAL
fun Fragment.snackBarShortGlobal(message: CharSequence?): Snackbar? =
  activity?.snackBarShort(message)

fun Fragment.snackBarLongGlobal(message: CharSequence?): Snackbar? =
  activity?.snackBarLong(message)

