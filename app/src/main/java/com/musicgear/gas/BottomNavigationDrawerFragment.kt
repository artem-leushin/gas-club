package com.musicgear.gas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.musicgear.gas.databinding.FragmentBottomSheetBinding
import com.musicgear.gas.domain.interactor.ObserveUserUseCase
import com.musicgear.gas.utils.basecomponents.dialogs.BaseBindingBottomDialogFragment
import com.musicgear.gas.utils.imageloading.ImageLoader
import com.musicgear.gas.utils.rx.applySchedulers
import com.musicgear.gas.utils.rx.disposeOf
import com.musicgear.gas.utils.snackBarShortGlobal
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_bottom_sheet.iv_avatar
import kotlinx.android.synthetic.main.fragment_bottom_sheet.navigation_view
import kotlinx.android.synthetic.main.fragment_bottom_sheet.tv_name
import kotlinx.android.synthetic.main.fragment_bottom_sheet.tv_screen_name
import org.koin.android.ext.android.get

class BottomNavigationDrawerFragment :
  BaseBindingBottomDialogFragment<FragmentBottomSheetBinding>() {

  private val observeUser: ObserveUserUseCase = get()
  private val imageLoader: ImageLoader = get()
  private lateinit var disposable: Disposable

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
    disposable = observeUser.execute()
      .applySchedulers()
      .subscribe {
        tv_name.text = it.fullName
        tv_screen_name.text = it.screenName

        imageLoader.loadImg(
          iv_avatar,
          it.avatarUrl,
          ImageLoader.Args(transformCircle = true, transformCenterCrop = true)
        )
      }

    navigation_view.setNavigationItemSelectedListener {
      dismiss()
      when (it.itemId) {
        R.id.bottom_menu_categories,
        R.id.bottom_menu_black_list -> snackBarShortGlobal("Not implemented yet")?.show()
      }
      true
    }
  }

  override fun onDestroyView() {
    disposable.disposeOf()
    super.onDestroyView()
  }
}