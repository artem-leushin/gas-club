object Version {
  const val appVersionCode = 10000
  const val appVersionName = "1.0.0"

  const val minSdk = 21
  const val targetSdk = 29
  const val compileSdk = 29
  const val buildTools = "29.0.2"

  const val kotlin = "1.3.61"
  const val kotlinPlugin = "1.3.61"
  const val coroutinesAndroid = "1.0.1" // https://kotlinlang.org/

  const val jackson = "2.9.9"
  const val adapterDelegates = "4.2.0"
  const val threeTen = "1.2.2"
  const val vkSdk = "2.2.1"

  // Android Jetpack
  // https://developer.android.com/jetpack
  const val appCompat = "1.1.0"
  const val material = "1.1.0"
  const val constraintLayout = "2.0.0-beta4"
  const val viewpager2 = "1.0.0"
  const val lifecycle = "2.2.0"
  const val arch = "2.1.0"
  const val navigation = "2.3.0-alpha06"
  const val room = "2.2.5"
  const val multidex = "2.0.1"
  const val dataBindingCompiler = "3.1.2"

  // ktx
  // https://github.com/android/android-ktx
  const val ktxCore = "1.2.0"
  const val ktxCollections = "1.1.0"
  const val ktxFragment = "1.2.4"
  const val ktxRoom = "2.2.5"
  const val ktxLifecycle = "2.2.0"
  const val ktxNavigation = "2.3.0-alpha06"

  //rxExtensions
  const val rxJava3 = "3.0.0"            //https://github.com/ReactiveX/RxJava
  const val rxJava2 = "2.2.0"
  const val rxRelay = "2.0.0"           //https://github.com/JakeWharton/RxRelay
  const val rxReplayShare = "2.2.0"     //https://github.com/JakeWharton/RxReplayingShare
  const val rxKotlin2 = "2.2.0"          //https://github.com/ReactiveX/RxKotlin
  const val rxKotlin3 = "3.0.0"
  const val rxAndroid2 = "2.1.1"         //https://github.com/ReactiveX/RxAndroid
  const val rxAndroid3 = "3.0.0"
  const val rxBinding2 = "2.2.0"         //https://github.com/JakeWharton/RxBinding
  const val rxBinding3 = "3.1.0"
  const val rxNetwork = "3.0.1"         //https://github.com/pwittchen/ReactiveNetwork
  const val rxPermissions = "0.10.2"    //https://github.com/tbruyelle/RxPermissions

  // Networking
  const val okHttp = "4.6.0"
  const val retrofit = "2.8.1"
  const val glide = "4.11.0"
  const val loganSquare = "1.3.7"
  const val retrofitLoganSquare = "1.4.1"

  // DI
  const val koin = "2.1.5"  //https://github.com/InsertKoinIO/koin

  // Debugging
  const val debugDb = "1.0.4"    //https://github.com/amitshekhariitbhu/Android-Debug-Database

  // Testing
  const val jUnit = "4.13"
  const val archCoreTesting = "1.1.1"
  const val espresso = "3.2.0"
  const val mockitoKotlin = "2.0.0-RC1"
  const val mockito = "2.23.4"
  const val hamcrest = "2.1"
  const val robolectric = "4.1"
}

@Suppress("Reformat")
object Deps {
  const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin}"

  const val vkSdk = "com.vk:androidsdk:${Version.vkSdk}"
  const val adapterDelegates = "com.hannesdorfmann:adapterdelegates4:${Version.adapterDelegates}"
  const val threeTenTime = "com.jakewharton.threetenabp:threetenabp:${Version.threeTen}"

  // Android
  const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
  const val material = "com.google.android.material:material:${Version.material}"

  // Coroutines
  const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutinesAndroid}"
  const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutinesAndroid}"

  // Jetpack main
  const val lifecycle = "androidx.lifecycle:lifecycle-runtime:${Version.lifecycle}"
  const val lifecycleKapt = "androidx.lifecycle:lifecycle-common-java8:${Version.lifecycle}"
  const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycle}"
  const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle}"

  // Jetpack optional
  const val lifecycleService = "androidx.lifecycle:lifecycle-service:${Version.lifecycle}"
  const val lifecycleProcess = "androidx.lifecycle:lifecycle-process:${Version.lifecycle}"
  const val viewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Version.lifecycle}"
  const val lifecycleReactiveStreams = "androidx.lifecycle:lifecycle-reactivestreams:${Version.lifecycle}"

  // Jetpack persistance
  const val paging = "androidx.paging:paging-runtime:${Version.lifecycle}"
  const val room = "androidx.room:room-runtime:${Version.lifecycle}"
  const val roomRx = "androidx.room:room-rxjava2:${Version.lifecycle}"
  const val roomCompiler = "androidx.room:room-compiler:${Version.lifecycle}"

  // Jetpack navigation
  const val navigation = "androidx.navigation:navigation-fragment-ktx:${Version.lifecycle}"
  const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Version.lifecycle}"
  const val navigationDynamic = "androidx.navigation:navigation-dynamic-features-fragment:${Version.lifecycle}"

  // KTX
  const val ktxCore = "androidx.core:core-ktx:${Version.ktxCore}"
  const val ktxCollections = "androidx.collection:collection-ktx:${Version.ktxCollections}"
  const val ktxFragment = "androidx.fragment:fragment-ktx:${Version.ktxFragment}"
  const val ktxRoom = "androidx.room:room-ktx:${Version.ktxRoom}"
  const val ktxLifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.ktxLifecycle}"
  const val ktxNavigationFragment = "androidx.navigation:navigation-fragment-ktx:${Version.ktxNavigation}"
  const val ktxNavigationRuntime = "androidx.navigation:navigation-runtime-ktx:${Version.ktxNavigation}"
  const val ktxNavigationUi = "androidx.navigation:navigation-ui-ktx:${Version.ktxNavigation}"

  // Koin core
  const val koinCore = "org.koin:koin-core:${Version.koin}"
  const val koinExperimental = "org.koin:koin-core-ext:${Version.koin}"
  const val koinTests = "org.koin:koin-test:${Version.koin}"

  // Koin Android
  const val koinAndroid = "org.koin:koin-android:${Version.koin}"
  const val koinAndroidExperimental = "org.koin:koin-androidx-ext:${Version.koin}"
  const val koinScope = "org.koin:koin-androidx-scope:${Version.koin}"
  const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Version.koin}"
  const val koinFragmentFactory = "org.koin:koin-androidx-fragment:${Version.koin}"

  // Network
  const val retrofit2 = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
  const val retrofit2RxJava2Adapter = "com.squareup.retrofit2:adapter-rxjava2:${Version.retrofit}"
  const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okHttp}"
  const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Version.okHttp}"
  const val jacksonParser = "com.fasterxml.jackson.core:jackson-core:${Version.jackson}"

  // Image loading
  const val glide = "com.github.bumptech.glide:glide:${Version.glide}"
  const val glideOkhttpIntegration = "com.github.bumptech.glide:okhttp3-integration:${Version.glide}"
  const val glideCompiler = "com.github.bumptech.glide:compiler:${Version.glide}"

  // Serialization
  const val loganSquare = "com.bluelinelabs:logansquare:${Version.loganSquare}"
  const val loganSquarecompiler = "com.bluelinelabs:logansquare-compiler:${Version.loganSquare}"
  const val retrofitLoganSquare = "com.github.aurae.retrofit2:converter-logansquare:${Version.loganSquare}"

  // Tests
  const val jUnit = "junit:junit:${Version.jUnit}"
  const val jUnitExt = "androidx.test.ext:junit:${Version.archCoreTesting}"
  const val mockito = "org.mockito:mockito-core:${Version.mockito}"
  const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Version.mockitoKotlin}"
  const val espresso = "androidx.test.espresso:espresso-core:${Version.espresso}"
  const val testNavigation = "androidx.navigation:navigation-testing:${Version.navigation}"
  const val testArch = "androidx.arch.core:core-testing:${Version.arch}"
  const val testRoom = "androidx.room:room-testing:${Version.room}"
  const val testOkHttpMockServer = "com.squareup.okhttp3:mockwebserver:${Version.okHttp}"
}

