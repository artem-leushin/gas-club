package com.musicgear.gas.login

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.musicgear.gas.domain.exception.DomainException
import com.musicgear.gas.login.LoginView.State
import com.musicgear.gas.login.databinding.DataBindingIdlingResourceRule
import com.musicgear.gas.utils.imageloading.bindingadapter.ImageBindingAdapter
import io.mockk.mockk
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginTest : KoinTest {

  private lateinit var scenario: FragmentScenario<LoginFragment>
  private val context = InstrumentationRegistry.getInstrumentation().targetContext
  private val vm: LoginViewModel = mockk(relaxed = true)
  private val bindingComponent = object : DataBindingComponent {
    override fun getImageBindingAdapter(): ImageBindingAdapter = mockk(relaxed = true)
  }

  @Rule
  @JvmField
  val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

  @Before
  fun setup() {
    startKoin {
      androidLogger()
      androidContext(context)
      modules(module { viewModel { vm } })
    }
    DataBindingUtil.setDefaultComponent(bindingComponent)
    runLoginFragment()
    scenario.moveToState(Lifecycle.State.RESUMED)
  }

  @After
  fun tearDown() {
    stopKoin()
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
  fun afterStartUp_imagesAreLoaded() {
    scenario.onFragment { it.render(State()) }

    onView(withId(R.id.btnLogin))
      .check(matches(isDisplayed()))

    onView(withId(R.id.progress))
      .check(matches(Matchers.not(isDisplayed())))

    onView(withId(com.google.android.material.R.id.snackbar_text))
      .check(matches(withText("Failed to login")))
  }

  private fun runLoginFragment() {
    val factory = FragmentFactory()
    scenario =
      launchFragmentInContainer<LoginFragment>(factory = factory, themeResId = R.style.Theme_App)
    dataBindingIdlingResourceRule.monitorFragment(scenario)
  }
}

