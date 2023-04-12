package com.noor.charginganimation.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.noor.charginganimation.core.extensions.toast
import com.noor.charginganimation.databinding.ActivityBatteryInfoBinding


class BatteryInfoActivity : AppCompatActivity() {
    private val binding: ActivityBatteryInfoBinding by lazy {
        ActivityBatteryInfoBinding.inflate(layoutInflater)
    }

    private var intentFilter: IntentFilter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentFilter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        intentFilter = IntentFilter(Intent.ACTION_POWER_DISCONNECTED)
        intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        registerReceiver(broadcastReceiver, intentFilter)
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            updateBatteryData(intent)
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

            val capacity = getBatteryCapacity(this)

            if (capacity > 0) {
                stringBuilder.append("Capacity : $capacity mAh\n")
            }

            binding.textView.text = stringBuilder.toString()
        } else toast("No battery found!")
    }

    private fun getBatteryCapacity(ctx: Context): Long {
        val mBatteryManager = ctx.getSystemService(BATTERY_SERVICE) as BatteryManager
        val chargeCounter =
            mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
        val capacity = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        return (chargeCounter.toFloat() / capacity.toFloat() * 100f).toLong()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}