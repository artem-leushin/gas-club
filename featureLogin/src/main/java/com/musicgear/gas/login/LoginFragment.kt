package com.musicgear.gas.login

import com.musicgear.gas.login.LoginView.*
import com.musicgear.gas.login.databinding.FragmentLoginBinding
import com.musicgear.gas.utils.basecomponents.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment :
  BaseBindingFragment<FragmentLoginBinding, State, StateChange, LoginViewModel>(),
  LoginView {

  override val viewModel: LoginViewModel by viewModel()

  override fun layoutResId(): Int = R.layout.fragment_login

  override fun initIntents() {
  }

  override fun observeViewState() {
  }

  override fun render(state: State) {
  }
}