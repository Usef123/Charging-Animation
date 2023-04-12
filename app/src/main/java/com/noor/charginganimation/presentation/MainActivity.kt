package com.noor.charginganimation.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.noor.charginganimation.core.extensions.click
import com.noor.charginganimation.core.utils.PrefUtils.isAlwaysOn
import com.noor.charginganimation.databinding.ActivityMainBinding
import com.noor.charginganimation.domain.services.ForegroundService

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ContextCompat.startForegroundService(this, Intent(this, ForegroundService::class.java))

        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            isAlwaysOn = isChecked
        }

        binding.buttonBatteryInfo.click {
            startActivity(Intent(this, BatteryInfoActivity::class.java))
        }

        binding.buttonDeviceInfo.click {
            startActivity(Intent(this, DeviceInfoActivity::class.java))
        }
    }

    override fun onStart() {
        binding.switch1.isChecked = isAlwaysOn
        super.onStart()
    }

    /*override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Timber.d("onKeyDown:")
            return true
        }
        return super.onKeyDown(keyCode, event)
    }*/
}