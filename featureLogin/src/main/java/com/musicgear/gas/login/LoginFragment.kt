package com.musicgear.gas.login

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionSet
import com.musicgear.gas.domain.entity.AuthBundle
import com.musicgear.gas.login.LoginView.Intent.ProceedLogin
import com.musicgear.gas.login.LoginView.Intent.StartLogin
import com.musicgear.gas.login.LoginView.State
import com.musicgear.gas.login.LoginView.StateChange
import com.musicgear.gas.login.databinding.FragmentLoginBinding
import com.musicgear.gas.utils.basecomponents.BaseBindingFragment
import com.musicgear.gas.utils.rx.plusAssign
import com.musicgear.gas.utils.rx.safeClicks
import com.musicgear.gas.utils.snackBarShort
import kotlinx.android.synthetic.main.fragment_login.btnLogin
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment :
  BaseBindingFragment<FragmentLoginBinding, State, StateChange, LoginViewModel>(),
  LoginView {

  override val viewModel: LoginViewModel by viewModel()

  override fun layoutResId(): Int = R.layout.fragment_login

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    postponeEnterTransition()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    prepareEnterTransition()
    return viewBinding!!.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    startPostponedEnterTransition()
  }

  private fun prepareEnterTransition() {
    sharedElementEnterTransition = TransitionSet().apply {
      allowEnterTransitionOverlap = true
      addTransition(ChangeBounds())
      addTransition(ChangeImageTransform())
    }
    enterTransition = Slide(Gravity.BOTTOM).apply {
      startDelay = (sharedElementEnterTransition as Transition).duration + 300
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    viewModel.publishViewIntent(ProceedLogin(AuthBundle(requestCode, resultCode, data)))
  }

  override fun initIntents() {
    viewSubscriptions += btnLogin.safeClicks { StartLogin(requireActivity()) }
      .subscribe(viewModel.viewIntentsConsumer())
  }

  override fun render(state: State) {
    viewBinding!!.loading = state.loading
    state.error?.message?.let { snackBarShort(it)?.show() }
  }
}