package com.noor.charginganimation.presentation.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.BatteryManager
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.noor.charginganimation.R
import com.noor.charginganimation.core.extensions.toast
import com.noor.charginganimation.databinding.FragmentBatteryInfoBinding
import me.itangqi.waveloadingview.WaveLoadingView
import kotlin.math.roundToInt


class BatteryInfoFragment : Fragment() {
    private var binding: FragmentBatteryInfoBinding? = null

    private var intentFilter: IntentFilter? = null
    private var cAnimator: WaveLoadingView? = null
    private var flashAnimation: LottieAnimationView? = null


    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            updateBatteryData(intent)
        }
    }

    companion object {
        private const val ANIM_DURATION = 30000
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBatteryInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intentFilter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        intentFilter = IntentFilter(Intent.ACTION_POWER_DISCONNECTED)
        intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        activity?.registerReceiver(broadcastReceiver, intentFilter)

        flashAnimation = binding?.flashAnimation
        cAnimator = binding?.chargingAnimator
        cAnimator?.setAnimDuration(ANIM_DURATION.toLong())
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        } else {
            AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
        }
    }

    private fun updateBatteryData(intent: Intent) {
        val present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false)

        if (present) {
            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)

            var statusLbl = ""
            when (status) {
                BatteryManager.BATTERY_STATUS_CHARGING -> {
                    statusLbl = "Charging"
                    cStatusViewInitializer(R.string.battery_status_charging, 2000, View.VISIBLE)
                }

                BatteryManager.BATTERY_STATUS_DISCHARGING -> {
                    statusLbl = "Discharging"
                    cStatusViewInitializer(
                        R.string.battery_status_discharging,
                        ANIM_DURATION,
                        View.INVISIBLE
                    )
                }

                BatteryManager.BATTERY_STATUS_FULL -> {
                    statusLbl = "Full"
                    cStatusViewInitializer(
                        R.string.battery_status_full,
                        ANIM_DURATION,
                        View.INVISIBLE
                    )
                }

                BatteryManager.BATTERY_STATUS_UNKNOWN -> {
                    statusLbl = "Unknown"
                    cStatusViewInitializer(
                        R.string.battery_status_unknown,
                        ANIM_DURATION,
                        View.INVISIBLE
                    )
                }

                BatteryManager.BATTERY_STATUS_NOT_CHARGING -> {
                    statusLbl = "Not Charging"
                    cStatusViewInitializer(
                        R.string.battery_status_not_charging,
                        ANIM_DURATION,
                        View.INVISIBLE
                    )
                }

                else -> {
                    statusLbl = "None"
                }
            }

            if (statusLbl.isNotEmpty()) {
                binding?.tvBatteryStatus?.text = statusLbl
            }

            // Calculate Battery Percentage ...
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)

            if (level != -1) {
                cAnimator?.centerTitle = "$level%"
                cAnimator?.progressValue = level

                when (level) {
                    1, 2, 3, 4, 5 -> cAnimator?.waveColor = Color.rgb(255, 0, 0)
                    6, 7, 8, 9, 10 -> cAnimator?.waveColor = Color.rgb(255, 0, 0)
                    11, 12, 13, 14, 15 -> cAnimator?.waveColor = Color.rgb(249, 64, 0)
                    16, 17, 18, 19, 20 -> cAnimator?.waveColor = Color.rgb(245, 80, 0)
                    21, 22, 23, 24, 25 -> cAnimator?.waveColor = Color.rgb(240, 95, 0)
                    26, 27, 28, 29, 30 -> cAnimator?.waveColor = Color.rgb(235, 107, 0)
                    31, 32, 33, 34, 35 -> cAnimator?.waveColor = Color.rgb(228, 119, 0)
                    36, 37, 38, 39, 40 -> cAnimator?.waveColor = Color.rgb(221, 130, 0)
                    41, 42, 43, 44, 45 -> cAnimator?.waveColor = Color.rgb(213, 140, 0)
                    46, 47, 48, 49, 50 -> cAnimator?.waveColor = Color.rgb(204, 150, 0)
                    51, 52, 53, 54, 55 -> cAnimator?.waveColor = Color.rgb(194, 159, 0)
                    56, 57, 58, 59, 60 -> cAnimator?.waveColor = Color.rgb(184, 168, 0)
                    61, 62, 63, 64, 65 -> cAnimator?.waveColor = Color.rgb(173, 176, 0)
                    66, 67, 68, 69, 70 -> cAnimator?.waveColor = Color.rgb(161, 184, 0)
                    71, 72, 73, 74, 75 -> cAnimator?.waveColor = Color.rgb(148, 191, 0)
                    76, 77, 78, 79, 80 -> cAnimator?.waveColor = Color.rgb(133, 199, 0)
                    81, 82, 83, 84, 85 -> cAnimator?.waveColor = Color.rgb(116, 206, 0)
                    86, 87, 88, 89, 90 -> cAnimator?.waveColor = Color.rgb(96, 212, 0)
                    91, 92, 93, 94, 95 -> cAnimator?.waveColor = Color.rgb(68, 219, 0)
                    96, 97, 98, 99, 100 -> cAnimator?.waveColor = Color.rgb(0, 225, 0)
                    else -> cAnimator?.waveColor = Color.rgb(0, 255, 0)
                }
            }

            val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)

            if (temperature > 0) {
                val temp = temperature / 10f
                binding?.tvBatteryTemp?.text = "${temp.roundToInt()}°C"
            }

            val health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)
            var healthLbl = ""

            when (health) {
                BatteryManager.BATTERY_HEALTH_COLD ->
                    healthLbl = "Cold"

                BatteryManager.BATTERY_HEALTH_DEAD ->
                    healthLbl = "Dead"

                BatteryManager.BATTERY_HEALTH_GOOD ->
                    healthLbl = "Good"

                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE ->
                    healthLbl = "Over Voltage"

                BatteryManager.BATTERY_HEALTH_OVERHEAT ->
                    healthLbl = "Overheat"

                BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE ->
                    healthLbl = "unspecified_failure"

                BatteryManager.BATTERY_HEALTH_UNKNOWN ->
                    healthLbl = "Health Unknown"
            }

            if (healthLbl.isNotEmpty()) binding?.tvBatteryHealth?.text = healthLbl

            val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)

            if (voltage > 0) {
                binding?.tvBatteryVoltage?.text = "$voltage mV"

                /*val capacity = Utils.getBatteryCapacity(requireContext())

                if (capacity > 0) {
                    stringBuilder.append("Capacity: $capacity mAh\n")
                }*/
            }

            if (intent.extras != null) {
                val technology = intent.extras?.getString(BatteryManager.EXTRA_TECHNOLOGY)
                if ("" != technology) {
                    binding?.tvBatteryType?.text = technology
                }
            }

            /*val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)

            val pluggedLbl: String = when (plugged) {
                BatteryManager.BATTERY_PLUGGED_WIRELESS -> "Wireless"
                BatteryManager.BATTERY_PLUGGED_USB -> "USB"
                BatteryManager.BATTERY_PLUGGED_AC -> "AC"
                else -> "None"
            }

            stringBuilder.append("Charging Type: $pluggedLbl\n")*/

        } else activity?.toast("No battery found!")
    }

    private fun cStatusViewInitializer(text: Int, duration: Int, visibility: Int) {
        binding?.tvBatteryStatus?.setText(text)
        cAnimator?.setAnimDuration(duration.toLong())
//        chargingParticleAnimation.resumeAnimation()
//        chargingParticleAnimation.setVisibility(visibility)
        flashAnimation?.resumeAnimation()
        flashAnimation?.visibility = visibility
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(broadcastReceiver)
    }
}