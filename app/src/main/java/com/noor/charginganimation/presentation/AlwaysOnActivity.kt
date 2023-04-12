package com.noor.charginganimation.presentation

import android.content.Intent
import android.content.IntentFilter
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.noor.charginganimation.R
import com.noor.charginganimation.core.utils.VersionUtils.hasP
import com.noor.charginganimation.databinding.ActivityAlwaysOnBinding
import com.noor.charginganimation.receivers.CombinedServiceReceiver
import com.noor.charginganimation.receivers.CombinedServiceReceiver.Companion.isAlwaysOnRunning

class AlwaysOnActivity : AppCompatActivity() {
    private val binding: ActivityAlwaysOnBinding by lazy {
        ActivityAlwaysOnBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (hasP()) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }

        val level = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            ?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            ?: 0
        binding.tvBattery.text = resources.getString(R.string.charged, level)
        /*val batteryIcn = binding.icBattery
        when {
            level >= 100 -> batteryIcn.setImageResource(R.drawable.ic_battery_100)
            level >= 90 -> batteryIcn.setImageResource(R.drawable.ic_battery_90)
            level >= 80 -> batteryIcn.setImageResource(R.drawable.ic_battery_80)
            level >= 60 -> batteryIcn.setImageResource(R.drawable.ic_battery_60)
            level >= 50 -> batteryIcn.setImageResource(R.drawable.ic_battery_50)
            level >= 30 -> batteryIcn.setImageResource(R.drawable.ic_battery_30)
            level >= 20 -> batteryIcn.setImageResource(R.drawable.ic_battery_20)
            level >= 0 -> batteryIcn.setImageResource(R.drawable.ic_battery_0)
            else -> batteryIcn.setImageResource(R.drawable.ic_battery_unknown)
        }*/
    }

    override fun onStart() {
        isAlwaysOnRunning = true
        super.onStart()
    }

    override fun onDestroy() {
        isAlwaysOnRunning = false
        super.onDestroy()
    }
}