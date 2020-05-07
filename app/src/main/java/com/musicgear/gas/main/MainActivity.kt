package com.musicgear.gas.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.transition.TransitionManager
import com.musicgear.gas.BottomNavigationDrawerFragment
import com.musicgear.gas.FragmentLifecycleListener
import com.musicgear.gas.R
import com.musicgear.gas.login.LoginFragment
import com.musicgear.gas.main.MainView.State
import com.musicgear.gas.main.MainView.StateChanges
import com.musicgear.gas.navigation.AppNavigator
import com.musicgear.gas.utils.basecomponents.BaseActivity
import com.musicgear.gas.utils.isOnScreen
import com.musicgear.gas.utils.snackBarShort
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.bottomAppBar
import kotlinx.android.synthetic.main.activity_main.cl_root
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<State, StateChanges, MainViewModel>(),
  MainView {

  private val navigator: AppNavigator = get()
  override val viewModel: MainViewModel by viewModel()
  override val viewSubscriptions: Disposable = CompositeDisposable()

  private val fragmentsLifecycleListener: FragmentManager.FragmentLifecycleCallbacks by lazy {
    FragmentLifecycleListener(viewModel::publishViewIntent)
  }

  override fun layoutResId(): Int = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    monitorChildFragments()
    setSupportActionBar(bottomAppBar)
    setupNavigator()
  }

  override fun onDestroy() {
    super.onDestroy()
    stopMonitoringChildFragments()
  }

  private fun setupNavigator() {
    val mainNavController =
      (supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment).navController

    navigator.setNavController(mainNavController)
    setupUiWithNavigation(mainNavController)
  }

  private fun setupUiWithNavigation(mainNavController: NavController) {
    val conf = AppBarConfiguration.Builder((setOf(R.id.categoriesFragment))).build()
    NavigationUI.setupWithNavController(toolbar, mainNavController, conf)
    bottomAppBar.setOnMenuItemClickListener { it.onNavDestinationSelected(mainNavController) }
  }

  private fun monitorChildFragments() =
    supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentsLifecycleListener, true)

  private fun stopMonitoringChildFragments() =
    supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentsLifecycleListener)

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.bottom_app_bar_menu, menu)
    return true
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    propagateVkResultToLoginFragment(requestCode, resultCode, data)
  }

  private fun propagateVkResultToLoginFragment(requestCode: Int, resultCode: Int, data: Intent?) {
    (supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment)
      .childFragmentManager.fragments.filterIsInstance<LoginFragment>()
      .firstOrNull()?.onActivityResult(requestCode, resultCode, data)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.bottom_menu_search -> snackBarShort(item.title)?.show()
      android.R.id.home -> BottomNavigationDrawerFragment().show(
        supportFragmentManager,
        "Bottom Menu"
      )
    }
    return true
  }

  override fun initIntents() {
    viewModel.viewIntentsConsumer()
  }

  override fun observeViewState() {
    viewModel.currentViewState().observe(this, Observer { render(it) })
  }

  override fun render(state: State) {
    if (state.hideControls) animateControlsOut()
    if (state.displayControls) animateControlsIn()
  }

  private fun animateControlsOut() {
    if (isOnScreen(toolbar).not() || isOnScreen(bottomAppBar).not()) return

    TransitionManager.beginDelayedTransition(cl_root)
    fab.visibility = View.GONE
    bottomAppBar.visibility = View.GONE
    toolbar.visibility = View.GONE
  }

  private fun animateControlsIn() {
    TransitionManager.beginDelayedTransition(cl_root)
    bottomAppBar.visibility = View.VISIBLE
    fab.visibility = View.VISIBLE
    toolbar.visibility = View.VISIBLE
  }
}

