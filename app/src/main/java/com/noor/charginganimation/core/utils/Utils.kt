package com.noor.charginganimation.core.utils

import android.content.Context
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity

object Utils {

    fun getBatteryCapacity(ctx: Context): Long {
        val mBatteryManager = ctx.getSystemService(AppCompatActivity.BATTERY_SERVICE) as BatteryManager
        val chargeCounter =
            mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
        val capacity = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        return (chargeCounter.toFloat() / capacity.toFloat() * 100f).toLong()
    }
}