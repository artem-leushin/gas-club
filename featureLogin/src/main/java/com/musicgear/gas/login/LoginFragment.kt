package com.musicgear.gas.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionSet
import com.musicgear.gas.domain.entity.AuthBundle
import com.musicgear.gas.login.LoginView.Event.ProceedLogin
import com.musicgear.gas.login.LoginView.Event.StartLogin
import com.musicgear.gas.login.LoginView.State
import com.musicgear.gas.login.LoginView.StateChange
import com.musicgear.gas.login.databinding.FragmentLoginBinding
import com.musicgear.gas.utils.basecomponents.BaseBindingFragment
import com.musicgear.gas.utils.rx.safeClicks
import com.musicgear.gas.utils.snackBarShort
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_login.btnLogin
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment :
  BaseBindingFragment<FragmentLoginBinding, State, StateChange, LoginViewModel>(),
  LoginView {

  override val viewModel: LoginViewModel by viewModel()
  override val viewSubscriptions: CompositeDisposable = CompositeDisposable()

  override fun layoutResId(): Int = R.layout.fragment_login

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    sharedElementEnterTransition = TransitionSet().apply {
      addTransition(ChangeBounds())
      addTransition(ChangeImageTransform())
    }
    return viewBinding!!.root
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    viewModel.publishViewIntent(ProceedLogin(AuthBundle(requestCode, resultCode, data)))
  }

  override fun initIntents() {
    viewSubscriptions += btnLogin.safeClicks { StartLogin(activity!!) }
      .subscribe(viewModel.viewIntentsConsumer())
  }

  override fun render(state: State) {
    state.error?.message?.let { snackBarShort(it)?.show() }
  }
}