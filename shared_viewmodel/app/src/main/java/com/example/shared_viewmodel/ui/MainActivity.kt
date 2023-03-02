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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shared_viewmodel.util.FragmentWatcher
import com.example.shared_viewmodel.R
import com.example.shared_viewmodel.databinding.ActivityMainBinding
import com.example.shared_viewmodel.databinding.LayoutBottomBarBinding
import com.example.shared_viewmodel.ui.webView.WebViewFragment
import com.example.shared_viewmodel.util.Logger
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!! as NavHostFragment).findNavController()

        // navigation destination listener
        navController.addOnDestinationChangedListener { _, destination, args ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.bottomBar.root.visibility = View.VISIBLE
                    viewModel.webFragmentCount = 0
                }

                R.id.leftMenuFragment -> {
                    binding.bottomBar.root.visibility = View.VISIBLE
                }

                else -> {
                    binding.bottomBar.root.visibility = View.GONE
                }
            }

            args?.get("result")?.let {
                when (it) {
                    "webview" -> AlertDialog.Builder(this@MainActivity).setMessage("webview result~").setPositiveButton("ok") {_,_ ->}.create().show()
                    "leftmenu" -> AlertDialog.Builder(this@MainActivity).setMessage("leftmenu result~").setPositiveButton("ok") {_,_ ->}.create().show()

                }
            }
        }

        // fragment lifecycle listener
        supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentWatcher(this), true)

        initBottomBar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initBottomBar() =with (binding.bottomBar){
        home.setOnClickListener {
        }
        category.setOnClickListener {
            navController.navigate(R.id.action_home_to_leftMenu, Bundle().apply { putString("result", "")})
        }
        brand.setOnClickListener {
            navController.navigate(R.id.action_home_to_webview)
        }
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.homeFragment -> {
                AlertDialog.Builder(this@MainActivity).setMessage("stahp").setPositiveButton("ok"){ _, _ -> super.onBackPressed() }.create().show()
            }
            R.id.webViewFragment, R.id.leftMenuFragment -> {
                super.onBackPressed()
            }
            else -> navController.navigate(R.id.homeFragment)
        }
    }
}