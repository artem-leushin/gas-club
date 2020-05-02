package com.musicgear.gas.utils.basecomponents.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.musicgear.gas.utils.R

abstract class BaseBottomDialogFragment : BottomSheetDialogFragment() {

  protected var createdFirstTime: Boolean = true

  abstract fun layoutResId(): Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    createdFirstTime = savedInstanceState == null
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(layoutResId(), container, false)

  protected fun expandToFullHeight(dialog: DialogInterface) {
    val bottomSheet = (dialog as BottomSheetDialog).findViewById<View>(R.id.design_bottom_sheet)!!
    val behavior = BottomSheetBehavior.from(bottomSheet)
    behavior.peekHeight = bottomSheet.height
  }
}

abstract class BaseBindingBottomDialogFragment<B : ViewDataBinding> : BaseBottomDialogFragment() {

  protected var viewBinding: B? = null
    private set

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = DataBindingUtil.inflate<B>(inflater, layoutResId(), container, false)
    .run {
      viewBinding = this
      root
    }

  override fun onDestroyView() {
    super.onDestroyView()
    viewBinding = null
  }
}