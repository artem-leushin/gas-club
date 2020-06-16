package com.musicgear.gas

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.musicgear.gas.domain.entity.AuthBundle
import com.musicgear.gas.domain.exception.VkLoginFailedException
import com.musicgear.gas.domain.interactor.LoginWithVkUseCase
import com.musicgear.gas.domain.interactor.ProceedLoginWithVkUseCase
import com.musicgear.gas.login.LoginCoordinator
import com.musicgear.gas.login.LoginView.Intent.ProceedLogin
import com.musicgear.gas.login.LoginView.Intent.StartLogin
import com.musicgear.gas.login.LoginView.State
import com.musicgear.gas.login.LoginView.StateChange.Error
import com.musicgear.gas.login.LoginView.StateChange.HideError
import com.musicgear.gas.login.LoginView.StateChange.StartLoading
import com.musicgear.gas.login.LoginViewModel
import com.musicgear.gas.utils.RxTestSchedulerRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

  @get:Rule
  open val rxRule = RxTestSchedulerRule()

  @get:Rule
  open val executorRule = InstantTaskExecutorRule()

  private lateinit var vm: LoginViewModel
  private val loginUseCase: LoginWithVkUseCase = mockk()
  private val proceedLoginUseCase: ProceedLoginWithVkUseCase = mockk()
  private val coordinator: LoginCoordinator = mockk(relaxed = true)

  private val activity: Activity = mockk(relaxed = true)

  @Before
  fun setup() {
    vm = LoginViewModel(loginUseCase, proceedLoginUseCase, coordinator)
    vm.currentViewState().observeForever { }
    vm.viewIntentsConsumer()
  }

  @Test
  fun `startLogin renders loading state`() {
    every { loginUseCase.execute(activity) } returns Completable.complete()

    vm.publishViewIntent(StartLogin(activity))

    Assert.assertEquals(vm.currentViewState().value, State(loading = true))
  }

  @Test
  fun `startLogin error emits state changes`() {
    val error = VkLoginFailedException(101)
    every { loginUseCase.execute(activity) } returns Completable.error(error)

    val subscription = vm.viewIntents(vm.viewIntentsConsumer())
      .test()
    vm.publishViewIntent(StartLogin(activity))

    subscription.assertValueCount(3)
      .assertValues(StartLoading, Error(error), HideError)
  }

  @Test
  fun `startLogin error renders states`() {
    val error = VkLoginFailedException(101)
    every { loginUseCase.execute(activity) } returns Completable.error(error)

    val renderedStates = mutableListOf<State>()
    vm.currentViewState().observeForever { renderedStates += it }
    vm.publishViewIntent(StartLogin(activity))

    Assert.assertEquals(
      renderedStates, listOf(
        State(),
        State(loading = true),
        State(error = error),
        State(error = null)
      )
    )
  }

  @Test
  fun `proceedLogin renders loading state and navigates to home screen on success`() {
    val bundle = AuthBundle()
    every { proceedLoginUseCase.execute(bundle) } returns Completable.complete()

    vm.publishViewIntent(ProceedLogin(bundle))

    Assert.assertEquals(State(loading = true), vm.currentViewState().value)
    verify { coordinator.goToMusicGear() }
  }

  @Test
  fun `proceedLogin error emits state changes`() {
    val bundle = AuthBundle()
    val error = VkLoginFailedException(101)
    every { proceedLoginUseCase.execute(bundle) } returns Completable.error(error)

    val subscription = vm.viewIntents(vm.viewIntentsConsumer())
      .test()
    vm.publishViewIntent(ProceedLogin(bundle))

    subscription.assertValueCount(3)
      .assertValues(StartLoading, Error(error), HideError)

    verify(exactly = 0) { coordinator.goToMusicGear() }
  }

  @Test
  fun `proceedLogin error emits renders states`() {
    val bundle = AuthBundle()
    val error = VkLoginFailedException(101)
    every { proceedLoginUseCase.execute(bundle) } returns Completable.error(error)

    val renderedStates = mutableListOf<State>()
    vm.currentViewState().observeForever { renderedStates += it }
    vm.publishViewIntent(ProceedLogin(bundle))

    Assert.assertEquals(
      renderedStates, listOf(
        State(),
        State(loading = true),
        State(error = error),
        State(error = null)
      )
    )

    verify(exactly = 0) { coordinator.goToMusicGear() }
  }
}
