package com.musicgear.gas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.musicgear.gas.utils.snackBarShort
import kotlinx.android.synthetic.main.fragment_bottom_sheet.navigation_view

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    navigation_view.setNavigationItemSelectedListener {
      when (it.itemId) {
        R.id.bottom_menu_bookmarks -> snackBarShort(it.title)?.show()
        R.id.bottom_menu_black_list -> snackBarShort(it.title)?.show()
      }
      true
    }
  }
}