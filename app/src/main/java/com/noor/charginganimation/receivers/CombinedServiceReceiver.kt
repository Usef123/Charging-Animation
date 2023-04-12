package com.noor.charginganimation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.noor.charginganimation.core.utils.PrefUtils.isAlwaysOn
import com.noor.charginganimation.presentation.AlwaysOnActivity
import timber.log.Timber

class CombinedServiceReceiver: BroadcastReceiver() {

    private var isCharging = false

    companion object {
        var isAlwaysOnRunning: Boolean = false
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                isCharging = true
                Timber.d("onReceive: power connected!")
                if (isAlwaysOn) {
                    context?.startActivity(
                        Intent(context, AlwaysOnActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                }
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                isCharging = false
                Timber.d("onReceive: power disconnected!")
//                Toast.makeText(context, "disconnected!", Toast.LENGTH_SHORT).show()
//                AlwaysOnActivity.finish()
            }
            Intent.ACTION_SCREEN_OFF -> {
                Timber.d("onReceive: screen off!")
                if (isCharging && !isAlwaysOnRunning) {
                    context?.startActivity(
                        Intent(context, AlwaysOnActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                }
            }
            Intent.ACTION_SCREEN_ON -> {
                Timber.d("onReceive: screen on!")
            }
        }
    }
}