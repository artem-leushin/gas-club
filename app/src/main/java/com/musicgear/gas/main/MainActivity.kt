package com.musicgear.gas.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.transition.TransitionManager
import com.google.android.material.bottomappbar.BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
import com.google.android.material.bottomappbar.BottomAppBar.FAB_ALIGNMENT_MODE_END
import com.musicgear.gas.BottomNavigationDrawerFragment
import com.musicgear.gas.FragmentLifecycleListener
import com.musicgear.gas.R
import com.musicgear.gas.login.LoginFragment
import com.musicgear.gas.main.MainView.State
import com.musicgear.gas.main.MainView.StateChange
import com.musicgear.gas.main.MainView.StateChange.BottomAppBarMode.None
import com.musicgear.gas.main.MainView.StateChange.BottomAppBarMode.PostPhoto
import com.musicgear.gas.main.MainView.StateChange.BottomAppBarMode.TalkToSeller
import com.musicgear.gas.navigation.AppNavigator
import com.musicgear.gas.utils.basecomponents.BaseActivity
import com.musicgear.gas.utils.delayAction
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

class MainActivity : BaseActivity<State, StateChange, MainViewModel>(),
  MainView {

  private val navigator: AppNavigator = get()
  override val viewModel: MainViewModel by viewModel()
  override val viewSubscriptions: Disposable = CompositeDisposable()

  private val talkToSellerDrawable: Drawable by lazy {
    ResourcesCompat.getDrawable(resources, R.drawable.ic_chat_bubble, theme)!!
  }

  private val takePhotoDrawable: Drawable by lazy {
    ResourcesCompat.getDrawable(resources, R.drawable.ic_photo_add, theme)!!
  }

  private val fragmentsLifecycleListener: FragmentManager.FragmentLifecycleCallbacks by lazy {
    FragmentLifecycleListener(viewModel::sendIntent)
  }

  override fun layoutResId(): Int = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    monitorChildFragments()
    setupNavigator()
    fab.setOnClickListener { snackBarShort("Not implemented yet")?.show() }
  }

  override fun onDestroy() {
    super.onDestroy()
    stopMonitoringChildFragments()
  }

  private fun setupNavigator() {
    val mainNavController =
      (supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment).navController

    navigator.setNavController(mainNavController)
    setupToolbar(mainNavController)
    setupBottomBar()
  }

  private fun setupToolbar(mainNavController: NavController) {
    val topDestinations =
      setOf(R.id.categoriesFragment, R.id.blacklistedUsersFragment, R.id.loginFragment)
    val conf = AppBarConfiguration.Builder(topDestinations).build()
    NavigationUI.setupWithNavController(toolbar, mainNavController, conf)
    toolbar.inflateMenu(R.menu.menu_top)
    toolbar.setOnMenuItemClickListener {
      when (it.itemId) {
        R.id.menu_action_logout -> {
          viewModel.sendIntent(MainView.Intent.Logout)
          true
        }
        R.id.menu_action_search -> {
          snackBarShort("Not implemented yet")?.show()
          true
        }
        else -> false
      }
    }
  }

  private fun setupBottomBar() {
    setSupportActionBar(bottomAppBar)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> BottomNavigationDrawerFragment().show(
        supportFragmentManager,
        "Bottom Menu"
      )
    }
    return true
  }

  private fun monitorChildFragments() =
    supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentsLifecycleListener, true)

  private fun stopMonitoringChildFragments() =
    supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentsLifecycleListener)

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    propagateVkResultToLoginFragment(requestCode, resultCode, data)
  }

  private fun propagateVkResultToLoginFragment(requestCode: Int, resultCode: Int, data: Intent?) {
    (supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment)
      .childFragmentManager.fragments.filterIsInstance<LoginFragment>()
      .firstOrNull()?.onActivityResult(requestCode, resultCode, data)
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
    if (state.changeScreenMode) renderBottomBarMode(state)
  }

  private fun renderBottomBarMode(state: State) = when (state.screenMode) {
    PostPhoto -> {
      bottomAppBar.fabAlignmentMode = FAB_ALIGNMENT_MODE_CENTER
      fab.setImageDrawable(takePhotoDrawable)
      fab.show()
    }
    TalkToSeller -> {
      bottomAppBar.fabAlignmentMode = FAB_ALIGNMENT_MODE_END
      fab.setImageDrawable(talkToSellerDrawable)
    }
    None -> fab.hide()
  }

  private fun animateControlsOut() {
    if (isOnScreen(toolbar).not() || isOnScreen(bottomAppBar).not()) return
    fab.hide()
    delayAction { cl_root.transitionToStart() }
    TransitionManager.beginDelayedTransition(cl_root)
    bottomAppBar.visibility = View.GONE
  }

  private fun animateControlsIn() {
    if (isOnScreen(toolbar) || isOnScreen(bottomAppBar)) return
    delayAction { cl_root.transitionToEnd() }
    TransitionManager.beginDelayedTransition(cl_root)
    bottomAppBar.visibility = View.VISIBLE
  }
}