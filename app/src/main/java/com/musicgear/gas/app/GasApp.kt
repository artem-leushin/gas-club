package com.musicgear.gas.app

import android.app.Application
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.musicgear.di.loginModule
import com.musicgear.gas.BuildConfig
import com.musicgear.gas.di.categoriesModule
import com.musicgear.gas.di.dataModule
import com.musicgear.gas.di.detailsModule
import com.musicgear.gas.di.instrumentsModule
import com.musicgear.gas.di.localDbModule
import com.musicgear.gas.di.mainModule
import com.musicgear.gas.di.navigationModule
import com.musicgear.gas.di.remoteApiModule
import com.musicgear.gas.di.startModule
import com.musicgear.gas.di.utilsModule
import com.vk.api.sdk.VK
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GasApp : Application() {

  private val imageBindingComponent: DataBindingComponent by inject()

  override fun onCreate() {
    super.onCreate()
    VK.initialize(this)
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    startKoin {
      androidContext(this@GasApp)
      androidLogger(Level.DEBUG)
      modules(
        mainModule,
        remoteApiModule,
        localDbModule,
        dataModule,
        navigationModule,
        startModule,
        loginModule,
        categoriesModule,
        instrumentsModule,
        detailsModule,
        utilsModule
      )
    }

    DataBindingUtil.setDefaultComponent(imageBindingComponent)
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