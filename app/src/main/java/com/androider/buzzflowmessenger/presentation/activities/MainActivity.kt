package com.androider.buzzflowmessenger.presentation.activities

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.androider.buzzflowmessenger.R
import com.androider.buzzflowmessenger.databinding.ActivityMainBinding
import com.androider.buzzflowmessenger.presentation.viewmodel.SharedViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var navController: NavController
    private lateinit var toolBar: Toolbar
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private val component by lazy {
        (application as MyApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        toolBar = findViewById(R.id.toolBar)
        setSupportActionBar(toolBar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment
        navigationView = findViewById(R.id.navigationView)
        drawerLayout = findViewById(R.id.drawerLayout)

        if (navHostFragment != null) {
            navController = navHostFragment.navController
            navigationView.setupWithNavController(navController)
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.dashboardFragment
                )
                , drawerLayout)

            setupActionBarWithNavController(navController, appBarConfiguration)

            navController.addOnDestinationChangedListener{_, destination,_ ->
                if (destination.id == R.id.dashboardFragment
                    ||
                    destination.id == R.id.profileFragment
                    ||
                    destination.id == R.id.settingsFragment){
                    toolBar.visibility = View.VISIBLE
                } else {
                    toolBar.visibility = View.GONE
                }
                when (destination.id) {
                    R.id.dashboardFragment -> toolBar.title = "Chats"
                    R.id.profileFragment -> toolBar.title = "Profile"
                    R.id.settingsFragment -> toolBar.title = "Settings"
                    else -> toolBar.title = "Dashboard"
                }

            }

        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.logOut -> {

                    sendDataToFragment(true)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> {
                    val navigated = NavigationUI.onNavDestinationSelected(menuItem, navController)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    navigated
                }
            }
        }


    }

    private fun sendDataToFragment(value: Boolean) {
        val bundle = Bundle().apply {
            putBoolean("is_special_mode", value)
        }
        navController.navigate(R.id.dashboardFragment, bundle)
    }
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }



}