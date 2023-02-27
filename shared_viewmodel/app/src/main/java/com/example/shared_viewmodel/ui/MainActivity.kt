package com.example.shared_viewmodel.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shared_viewmodel.util.FragmentWatcher
import com.example.shared_viewmodel.R
import com.example.shared_viewmodel.databinding.ActivityMainBinding
import com.example.shared_viewmodel.databinding.LayoutBottomBarBinding
import com.example.shared_viewmodel.util.Logger
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    private lateinit var bottomBar: LayoutBottomBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!! as NavHostFragment).findNavController()

        binding.bottomBar.setupWithNavController(navController)
        // navigation destination listener
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Logger.v(" ${controller.currentBackStackEntry}")
            Logger.v(" ${destination.label}")
            Logger.v(" $arguments")

            val navBar = findViewById<BottomNavigationView>(R.id.bottom_bar)
            navBar.visibility = if (destination.label == "home") View.VISIBLE else View.GONE
        }

        // fragment lifecycle listener
        supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentWatcher(this), true)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initBottomBar(binding: LayoutBottomBarBinding) {
        bottomBar = binding
        with(binding) {
            home.setOnClickListener {
                Logger.v("탑니까~")
            }
            category.setOnClickListener {
                Logger.v("tanyago~")
            }
            brand.setOnClickListener {
                Logger.v("yo")
            }

            my.setOnClickListener {
                Logger.v("what up")
            }

            alarm.setOnClickListener {
                Logger.v("dude")
            }

            more.setOnClickListener {
                Logger.v("let me in")
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}