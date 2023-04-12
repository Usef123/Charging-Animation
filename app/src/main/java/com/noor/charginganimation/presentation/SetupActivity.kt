package com.noor.charginganimation.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.noor.charginganimation.R
import com.noor.charginganimation.core.extensions.click
import com.noor.charginganimation.core.utils.PrefUtils.isSetupComplete
import com.noor.charginganimation.databinding.ActivitySetupBinding
import com.noor.charginganimation.presentation.setup.DrawOverOtherAppsFragment
import com.noor.charginganimation.presentation.setup.PhoneStateFragment

class SetupActivity : AppCompatActivity() {
    private val binding: ActivitySetupBinding by lazy {
        ActivitySetupBinding.inflate(layoutInflater)
    }

    private var currentFragment = DRAW_OVER_OTHER_APPS_FRAGMENT
    private var isActionRequired = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        swapContentFragment(DrawOverOtherAppsFragment(), DRAW_OVER_OTHER_APPS_FRAGMENT)
        setupClickListeners()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (currentFragment > NO_FRAGMENT) currentFragment--
    }

    private fun setupClickListeners() {
        binding.skipBtn.click {
            when (currentFragment) {
                NO_FRAGMENT ->
                    swapContentFragment(DrawOverOtherAppsFragment(), DRAW_OVER_OTHER_APPS_FRAGMENT)
                DRAW_OVER_OTHER_APPS_FRAGMENT ->
                    swapContentFragment(PhoneStateFragment(), PHONE_STATE_FRAGMENT)
                PHONE_STATE_FRAGMENT ->
                    gotoMainActivityAndFinish()
            }
        }

        binding.continueBtn.click {
            when (currentFragment) {
                NO_FRAGMENT -> {
                    swapContentFragment(DrawOverOtherAppsFragment(), DRAW_OVER_OTHER_APPS_FRAGMENT)
                }
                DRAW_OVER_OTHER_APPS_FRAGMENT -> {
                    if (Settings.canDrawOverlays(this)) {
                        swapContentFragment(PhoneStateFragment(), PHONE_STATE_FRAGMENT)
                    } else {
                        startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
                        isActionRequired = true
                    }
                }
                PHONE_STATE_FRAGMENT -> {
                    if (applicationContext.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_PHONE_STATE),
                            0
                        )
                    } else {
                        isSetupComplete = true
                        gotoMainActivityAndFinish()
                    }
                }
            }
        }
    }

    private fun gotoMainActivityAndFinish() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onStart() {
        super.onStart()
        if (isActionRequired) {
            when (currentFragment) {
                DRAW_OVER_OTHER_APPS_FRAGMENT -> {
                    if (!Settings.canDrawOverlays(this)) {
                        Toast.makeText(this, R.string.setup_error, Toast.LENGTH_LONG).show()
                    } else {
                        swapContentFragment(PhoneStateFragment(), PHONE_STATE_FRAGMENT)
                    }
                }
            }
            isActionRequired = false
        }
    }

    private fun swapContentFragment(fragment: Fragment, id: Byte) {
        currentFragment = id
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.content, fragment, null)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    companion object {
        const val NO_FRAGMENT: Byte = 0
        const val DRAW_OVER_OTHER_APPS_FRAGMENT: Byte = 1
        const val PHONE_STATE_FRAGMENT: Byte = 2
    }
}