package com.example.shared_viewmodel.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shared_viewmodel.CommonConst.NAV_HOME
import com.example.shared_viewmodel.CommonConst.NAV_LEFT_MENU
import com.example.shared_viewmodel.CommonConst.NAV_WEB_VIEW
import com.example.shared_viewmodel.util.FragmentWatcher
import com.example.shared_viewmodel.R
import com.example.shared_viewmodel.databinding.ActivityMainBinding
import com.example.shared_viewmodel.databinding.LayoutBottomBarBinding
import com.example.shared_viewmodel.ui.webView.WebViewFragment
import com.example.shared_viewmodel.util.Logger
import com.example.shared_viewmodel.util.PopUpUtil.dialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<MainViewModel>()

    private var currentDestination = ""
    private val destinationChangeListener: (NavController, NavDestination, Bundle?) -> Unit = { controller, destination, _ ->
        binding.bottomBar.root.visibility =
            if (destination.id == R.id.homeFragment || destination.id == R.id.leftMenuFragment) View.VISIBLE
            else View.GONE

        when (destination.id) {
            R.id.homeFragment -> {
                viewModel.webFragmentCount = 0
                currentDestination = NAV_HOME
            }
            R.id.leftMenuFragment -> {
                currentDestination = NAV_LEFT_MENU
            }
            R.id.webViewFragment -> {
                currentDestination = NAV_WEB_VIEW
            }
            else -> {
                currentDestination = ""
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!! as NavHostFragment).findNavController()

        navController.addOnDestinationChangedListener(destinationChangeListener)

        supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentWatcher(this), true)

        initObserve()
        initBottomBar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initObserve() = with(viewModel) {
        webFragArgument.observe(this@MainActivity) {
            if (currentDestination != "home") Logger.v("web$it")
        }

        viewModelScope.launch {
            navController.visibleEntries.collect {
                Logger.v("youngin nav entry list ---------------------------------")
                Logger.v("youngin ${it.size}")
                Logger.v("youngin ${it.joinToString { it.destination.label ?: "" }}")
                Logger.v("youngin ${it.joinToString { it.lifecycle.currentState.toString()}}")
                Logger.v("youngin ${it.joinToString { it.maxLifecycle.toString() }}")
            }
        }
    }

    private fun initBottomBar() = with(binding.bottomBar) {
        home.setOnClickListener {
        }
        category.setOnClickListener {
            navController.navigate(R.id.action_home_to_leftMenu)
        }
        brand.setOnClickListener {
            navController.navigate(R.id.action_home_to_webview)
        }
        my.setOnClickListener {
            navController.navigate(R.id.action_home_to_cameraSearch)
        }
        alarm.setOnClickListener {
            navController.navigate(R.id.action_home_to_notify)
        }
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.homeFragment -> {
                dialog("finish the app?", ok = { finish() })
            }
            R.id.webViewFragment, R.id.leftMenuFragment -> {
                super.onBackPressed()
            }
            else -> navController.navigate(R.id.homeFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        navController.removeOnDestinationChangedListener(destinationChangeListener)
    }
}