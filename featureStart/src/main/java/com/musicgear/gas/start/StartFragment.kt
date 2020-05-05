package com.musicgear.gas.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.musicgear.gas.domain.entity.SessionStatus.LOGGED_IN
import com.musicgear.gas.domain.entity.SessionStatus.LOGGED_OUT
import com.musicgear.gas.domain.interactor.CheckIfUserIsLoggedInUseCase
import com.musicgear.gas.utils.basecomponents.BackPressHandler
import com.musicgear.gas.utils.imageloading.ImageLoader
import com.musicgear.gas.utils.rx.applySchedulers
import com.musicgear.gas.utils.rx.disposeOf
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_start.iv_logo
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class StartFragment : Fragment(), BackPressHandler {

  private val navigator: StartCoordinator by inject()
  private val checkLoginStatus: CheckIfUserIsLoggedInUseCase by inject()
  private val imageloader: ImageLoader by inject()
  private lateinit var disposable: Disposable

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_start, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    imageloader.loadAssetImg(
      iv_logo,
      "ic_logo.jpg",
      ImageLoader.Args()
    )

    animateBouncingLogo()
    disposable = checkLoginStatus
      .execute()
      .delay(1, TimeUnit.SECONDS)
      .applySchedulers()
      .subscribe { status ->
        when (status) {
          LOGGED_IN -> navigator.goToCategories()
          LOGGED_OUT -> navigator.goToLogin(iv_logo to iv_logo.transitionName)
        }
      }
  }

  override fun onDestroyView() {
    disposable.disposeOf()
    super.onDestroyView()
  }

  private fun animateBouncingLogo() {
    val scaleAnim = AnimationUtils.loadAnimation(context, R.anim.scale_bounce)
    iv_logo?.startAnimation(scaleAnim)
  }
}