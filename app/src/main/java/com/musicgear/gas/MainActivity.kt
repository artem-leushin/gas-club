package com.musicgear.gas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.musicgear.gas.MainView.*
import com.musicgear.gas.login.LoginFragment
import com.musicgear.gas.navigation.AppNavigator
import com.musicgear.gas.utils.basecomponents.BaseActivity
import com.musicgear.gas.utils.snackBarShort
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.bottomAppBar
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<State, StateChanges, MainViewModel>(), MainView {

  private val navigator: AppNavigator = get()
  override val viewModel: MainViewModel by viewModel()
  override val viewSubscriptions: Disposable = CompositeDisposable()

  override fun layoutResId(): Int = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setSupportActionBar(bottomAppBar)
    setupNavigation()
  }

  private fun setupNavigation() {
    val mainNavController =
      (supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment).navController
    navigator.setNavController(mainNavController)
    NavigationUI.setupWithNavController(toolbar, mainNavController)
    NavigationUI.setupWithNavController(bottomAppBar, mainNavController)
    bottomAppBar.setOnMenuItemClickListener {
      it.onNavDestinationSelected(mainNavController)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.bottom_app_bar_menu, menu)
    return true
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    propagateToLoginFragment(requestCode, resultCode, data)
  }

  private fun propagateToLoginFragment(requestCode: Int, resultCode: Int, data: Intent?) {
    (supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment)
      .childFragmentManager.fragments.filterIsInstance<LoginFragment>()
      .first()?.onActivityResult(requestCode, resultCode, data)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.bottom_menu_search -> snackBarShort(item.title)?.show()
      android.R.id.home -> {
        val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
        BottomNavigationDrawerFragment().show(supportFragmentManager, "Bottom Menu")
      }
    }
    return true
  }

  override fun initIntents() {
  }

  override fun observeViewState() {
  }

  override fun render(state: State) {
  }
}

