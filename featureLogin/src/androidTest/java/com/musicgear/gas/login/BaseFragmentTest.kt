package com.musicgear.gas.login

import android.content.Context
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.musicgear.gas.login.databinding.DataBindingIdlingResourceRule
import com.musicgear.gas.login.databinding.ImageBindingComponent
import com.musicgear.gas.utils.imageloading.GlideImageLoader
import com.musicgear.gas.utils.imageloading.ImageLoader
import com.musicgear.gas.utils.imageloading.bindingadapter.ImageBindingAdapter
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declare
import org.mockito.Mockito

abstract class BaseFragmentTest<F : Fragment> : KoinTest {

  protected lateinit var scenario: FragmentScenario<F>
  protected val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

  @Rule
  @JvmField
  val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

  @get:Rule
  val mockProvider = MockProviderRule.create { clazz ->
    Mockito.mock(clazz.java)
  }

  @Before
  fun setupKoin() {
    startKoin {
      androidLogger()
      androidContext(context)
    }
    declareImageBindingComponent()
  }

  @After
  fun tearDown() {
    stopKoin()
  }

  private fun declareImageBindingComponent() {
    declare<ImageLoader> { GlideImageLoader }
    declare { ImageBindingAdapter(imageLoader = get()) }
    declare<DataBindingComponent> { ImageBindingComponent(imageBindingAdapters = get()) }
    DataBindingUtil.setDefaultComponent(get())
  }
}