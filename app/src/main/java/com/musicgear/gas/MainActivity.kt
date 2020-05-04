package com.musicgear.gas

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.musicgear.gas.MainView.*
import com.musicgear.gas.utils.basecomponents.BaseActivity
import com.musicgear.gas.utils.snackBarShort
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.bottomAppBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<State, StateChanges, MainViewModel>(), MainView {

  override val viewModel: MainViewModel by viewModel()
  override val viewSubscriptions: Disposable = CompositeDisposable()

  override fun layoutResId(): Int = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setSupportActionBar(bottomAppBar)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.bottom_app_bar_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.bottom_menu_categories -> snackBarShort(item.title)?.show()
      R.id.bottom_menu_categories -> snackBarShort(item.title)?.show()
      android.R.id.home -> {
        val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
        BottomNavigationDrawerFragment().show(supportFragmentManager, bottomNavDrawerFragment.tag)
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

