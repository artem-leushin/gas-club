package com.musicgear.gas.app

import android.app.Application
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.musicgear.di.loginModule
import com.musicgear.gas.BuildConfig
import com.musicgear.gas.di.BindingComponent
import com.musicgear.gas.di.categoriesModule
import com.musicgear.gas.di.dataModule
import com.musicgear.gas.di.mainModule
import com.musicgear.gas.di.navigationModule
import com.musicgear.gas.di.startModule
import com.musicgear.gas.di.utilsModule
import com.musicgear.gas.instruments.di.instrumentsModule
import com.vk.api.sdk.VK
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GasApp : Application() {

  override fun onCreate() {
    super.onCreate()
    VK.initialize(this)
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    startKoin {
      androidContext(this@GasApp)
      androidLogger(Level.DEBUG)
      modules(
        mainModule,
        dataModule,
        navigationModule,
        startModule,
        loginModule,
        categoriesModule,
        instrumentsModule,
        utilsModule
      )
    }

    DataBindingUtil.setDefaultComponent(BindingComponent())
  }

  companion object {
    init {
      RxJavaPlugins.setErrorHandler {
        if (BuildConfig.DEBUG) {
          it.printStackTrace()
          Log.e("GasApp", it?.message ?: "")
        }
      }

      RxAndroidPlugins.setInitMainThreadSchedulerHandler {
        AndroidSchedulers.from(Looper.getMainLooper(), true)
      }
    }
  }
}