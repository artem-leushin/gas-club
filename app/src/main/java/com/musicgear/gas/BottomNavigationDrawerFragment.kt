package com.musicgear.gas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.musicgear.gas.databinding.FragmentBottomSheetBinding
import com.musicgear.gas.utils.basecomponents.dialogs.BaseBindingBottomDialogFragment
import com.musicgear.gas.utils.snackBarShort
import kotlinx.android.synthetic.main.fragment_bottom_sheet.navigation_view

class BottomNavigationDrawerFragment : BaseBindingBottomDialogFragment<FragmentBottomSheetBinding>(){

  override fun layoutResId(): Int = R.layout.fragment_bottom_sheet

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    return viewBinding!!.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    navigation_view.setNavigationItemSelectedListener {
      when (it.itemId) {
        R.id.bottom_menu_categories -> snackBarShort(it.title)?.show()
        R.id.bottom_menu_black_list -> snackBarShort(it.title)?.show()
      }
      true
    }
  }
}