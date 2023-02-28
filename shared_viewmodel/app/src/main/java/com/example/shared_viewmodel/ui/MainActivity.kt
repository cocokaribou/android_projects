package com.example.shared_viewmodel.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
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

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!! as NavHostFragment).findNavController()

        // navigation destination listener
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomBar.root.visibility =
                if (destination.label == "home" || destination.label == "leftMenu") View.VISIBLE
                else View.GONE
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
            navController.navigate(R.id.action_home_to_leftMenu)
        }
        brand.setOnClickListener {
            navController.navigate(R.id.action_home_to_webview)
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.label == "home") super.onBackPressed()
        else navController.navigateUp()
    }
}