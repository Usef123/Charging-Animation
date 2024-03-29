package com.noor.charginganimation.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.noor.charginganimation.R
import com.noor.charginganimation.core.extensions.click
import com.noor.charginganimation.core.extensions.hide
import com.noor.charginganimation.core.extensions.show
import com.noor.charginganimation.core.utils.PrefUtils.isAlwaysOn
import com.noor.charginganimation.databinding.ActivityMainBinding
import com.noor.charginganimation.domain.services.ForegroundService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomNavigation

        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.findNavController()
        bottomNavigationView.setupWithNavController(navController)

        handleBottomNavigationVisibility()

        ContextCompat.startForegroundService(this, Intent(this, ForegroundService::class.java))
    }

    /*override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Timber.d("onKeyDown:")
            return true
        }
        return super.onKeyDown(keyCode, event)
    }*/

    private fun handleBottomNavigationVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.animationsAndThemesFragment -> bottomNavigationView.show()
                R.id.homeFragment -> bottomNavigationView.show()
                R.id.settingsFragment -> bottomNavigationView.show()
                else -> bottomNavigationView.hide()
            }
        }
    }
}