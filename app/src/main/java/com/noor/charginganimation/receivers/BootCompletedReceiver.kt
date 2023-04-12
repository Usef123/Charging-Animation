package com.noor.charginganimation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.noor.charginganimation.domain.services.ForegroundService

class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (
            intent.action == Intent.ACTION_BOOT_COMPLETED
            || intent.action == Intent.ACTION_MY_PACKAGE_REPLACED
        ) {
            ContextCompat.startForegroundService(context, Intent(context, ForegroundService::class.java))
        }
    }
}