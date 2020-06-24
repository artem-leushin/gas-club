package com.musicgear.gas

import androidx.test.core.app.ApplicationProvider
import com.musicgear.di.loginModule
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
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.test.check.checkModules
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ModulesTest {

  @Test
  fun `data modules are correctly defined`() {
    checkModules {
      androidContext(ApplicationProvider.getApplicationContext())
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
  }
}