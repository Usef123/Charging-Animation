package com.noor.charginganimation.presentation.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.noor.charginganimation.core.extensions.toast
import com.noor.charginganimation.core.utils.Utils
import com.noor.charginganimation.databinding.FragmentBatteryInfoBinding

class BatteryInfoFragment : Fragment() {
    private var binding: FragmentBatteryInfoBinding? = null

    private var intentFilter: IntentFilter? = null

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            updateBatteryData(intent)
        }
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
        val stringBuilder = StringBuilder()

        if (present) {
            val health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)
            var healthLbl = ""

            when (health) {
                BatteryManager.BATTERY_HEALTH_COLD ->
                    healthLbl = "battery_health_cold"

                BatteryManager.BATTERY_HEALTH_DEAD ->
                    healthLbl = "battery_health_dead"

                BatteryManager.BATTERY_HEALTH_GOOD ->
                    healthLbl = "battery_health_good"

                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE ->
                    healthLbl = "battery_health_over_voltage"

                BatteryManager.BATTERY_HEALTH_OVERHEAT ->
                    healthLbl = "battery_health_overheat"

                BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE ->
                    healthLbl = "battery_health_unspecified_failure"

                BatteryManager.BATTERY_HEALTH_UNKNOWN ->
                    healthLbl = "Battery health unknown"
            }

            stringBuilder.append("Health: $healthLbl\n")

            // Calculate Battery Percentage ...
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)

            if (level != -1) {
                stringBuilder.append("Battery Pct : $level %\n")
            }

            val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)

            val pluggedLbl: String = when (plugged) {
                BatteryManager.BATTERY_PLUGGED_WIRELESS -> "battery_plugged_wireless"
                BatteryManager.BATTERY_PLUGGED_USB -> "battery_plugged_usb"
                BatteryManager.BATTERY_PLUGGED_AC -> "battery_plugged_ac"
                else -> "battery_plugged_none"
            }

            stringBuilder.append("Plugged : $pluggedLbl\n")

            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)

            val statusLbl: String = when (status) {
                BatteryManager.BATTERY_STATUS_CHARGING -> "battery_status_charging"
                BatteryManager.BATTERY_STATUS_DISCHARGING -> "battery_status_discharging"
                BatteryManager.BATTERY_STATUS_FULL -> "battery_status_full"
                BatteryManager.BATTERY_STATUS_UNKNOWN -> "BATTERY_STATUS_UNKNOWN"
                BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "battery_status_discharging"
                else -> "battery_status_discharging"
            }

            if (statusLbl.isNotEmpty()) {
                stringBuilder.append("Battery Charging Status : $statusLbl\n")
            }

            if (intent.extras != null) {
                val technology = intent.extras?.getString(BatteryManager.EXTRA_TECHNOLOGY)
                if ("" != technology) {
                    stringBuilder.append("Technology : $technology\n")
                }
            }

            val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)

            if (temperature > 0) {
                val temp = temperature / 10f
                stringBuilder.append("Temperature : $temp °C\n")
            }

            val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)

            if (voltage > 0) {
                stringBuilder.append("Voltage : $voltage mV\n")
            }

            val capacity = Utils.getBatteryCapacity(requireContext())

            if (capacity > 0) {
                stringBuilder.append("Capacity : $capacity mAh\n")
            }

            binding?.textView?.text = stringBuilder.toString()
        } else activity?.toast("No battery found!")
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(broadcastReceiver)
    }
}