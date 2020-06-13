package com.musicgear.gas.login

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.musicgear.gas.domain.exception.DomainException
import com.musicgear.gas.login.LoginView.State
import com.musicgear.gas.login.matchers.hasDrawable
import io.mockk.mockk
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginTest : BaseFragmentTest<LoginFragment>() {

  private val vm: LoginViewModel = mockk(relaxed = true)

  @Before
  fun setup() {
    loadKoinModules(module { viewModel { vm } })
    createAndRunScenario()
  }

  private fun createAndRunScenario() {
    scenario = launchFragmentInContainer(null, R.style.Theme_App, FragmentFactory())
    dataBindingIdlingResourceRule.monitorFragment(scenario)
    scenario.moveToState(Lifecycle.State.RESUMED)
  }

  @Test
  fun signInButton_startsProgress() {
    scenario.onFragment { it.render(State(loading = false)) }

    onView(withId(R.id.btnLogin))
      .check(matches(isDisplayed()))

    onView(withId(R.id.progress))
      .check(matches(Matchers.not(isDisplayed())))

    scenario.onFragment { it.render(State(loading = true)) }

    onView(withId(R.id.btnLogin))
      .check(matches(Matchers.not(isDisplayed())))

    onView(withId(R.id.progress))
      .check(matches(isDisplayed()))
  }

  @Test
  fun buttonHasSignInText() {
    scenario.onFragment { it.render(State()) }

    onView(withId(R.id.btnLogin))
      .check(matches(withText(context.getString(R.string.sign_in))))
  }

  @Test
  fun signInError_displaysSnackBar() {
    scenario.onFragment { it.render(State(error = DomainException("Failed to login"))) }

    onView(withId(R.id.btnLogin))
      .check(matches(isDisplayed()))

    onView(withId(R.id.progress))
      .check(matches(Matchers.not(isDisplayed())))

    onView(withId(com.google.android.material.R.id.snackbar_text))
      .check(matches(withText("Failed to login")))
  }

  @Test
  fun backgroundAndLogoAreLoaded() {
    scenario.onFragment { it.render(State()) }

    onView(withId(R.id.iv_logo))
      .check(matches(isDisplayed()))

    onView(withId(R.id.iv_logo))
      .check(matches(hasDrawable()))

    onView(withId(R.id.iv_background))
      .check(matches(isDisplayed()))

    onView(withId(R.id.iv_background))
      .check(matches(hasDrawable()))
  }
}