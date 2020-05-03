package com.musicgear.gas

import android.app.Application
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.musicgear.gas.data.di.dataModule
import com.musicgear.gas.di.mainModule
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GasApp : Application() {

  override fun onCreate() {
    super.onCreate()
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    startKoin {
      androidContext(this@GasApp)
      modules(
        mainModule,
        dataModule
      )
    }
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