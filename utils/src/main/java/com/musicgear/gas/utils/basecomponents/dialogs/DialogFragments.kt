package com.musicgear.gas.utils.basecomponents.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment() {

  protected var createdFirstTime: Boolean = true

  abstract fun layoutResId(): Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    createdFirstTime = savedInstanceState == null
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? = inflater.inflate(layoutResId(), container, false)
}

abstract class BaseBindingDialogFragment<B : ViewDataBinding> : BaseDialogFragment() {

  var viewBinding: B? = null
    private set

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? = DataBindingUtil.inflate<B>(inflater, layoutResId(), container, false).run {
    viewBinding = this
    root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    viewBinding = null
  }
}

