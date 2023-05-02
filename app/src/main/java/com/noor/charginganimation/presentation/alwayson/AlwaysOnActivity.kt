package com.noor.charginganimation.presentation.alwayson

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.noor.charginganimation.R
import com.noor.charginganimation.core.custom.DoubleTapDetector
import com.noor.charginganimation.core.utils.PrefUtils.isDoubleTapOn
import com.noor.charginganimation.core.utils.VersionUtils.hasOreo
import com.noor.charginganimation.databinding.ActivityAlwaysOnBinding
import com.noor.charginganimation.receivers.CombinedServiceReceiver.Companion.isAlwaysOnRunning
import timber.log.Timber

class AlwaysOnActivity : OffActivity() {
    private val binding: ActivityAlwaysOnBinding by lazy {
        ActivityAlwaysOnBinding.inflate(layoutInflater)
    }

    companion object {
        private var instance: AlwaysOnActivity? = null

        fun finish() {
            Timber.d("AlwaysOnActivity: always on finished!")
            instance?.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        turnOnScreen()
        fullscreen(binding.root)

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

        //DoubleTap
        if (isDoubleTapOn) {
            val doubleTapDetector = DoubleTapDetector({
                /*val duration = *//*prefs.get("ao_vibration", 64).toLong()*//* 64
                if (duration > 0) {
                    val vibrator =
                        getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (hasOreo()) {
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                duration.toLong(),
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        vibrator.vibrate(duration)
                    }
                }*/
                binding.root.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                finish()
            }, /*prefs.get(P.DOUBLE_TAP_SPEED, P.DOUBLE_TAP_SPEED_DEFAULT)*/ ViewConfiguration.getDoubleTapTimeout())

            binding.root.setOnTouchListener { v, event ->
                doubleTapDetector.onTouchEvent(event)
                v.performClick()
            }
        }
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