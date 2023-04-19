package com.noor.charginganimation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.noor.charginganimation.core.extensions.toast
import com.noor.charginganimation.core.utils.PrefUtils.isAlwaysOn
import com.noor.charginganimation.presentation.alwayson.AlwaysOnActivity
import timber.log.Timber

class CombinedServiceReceiver : BroadcastReceiver() {

    var isScreenOn: Boolean = true
    private var isCharging = false

    companion object {
        var isAlwaysOnRunning: Boolean = false
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                isCharging = true
                Timber.d("onReceive: power connected!")
                context?.toast("connected!")
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
                context?.toast("disconnected!")
                AlwaysOnActivity.finish()
            }
            Intent.ACTION_SCREEN_OFF -> {
                isScreenOn = false
                Timber.d("onReceive: screen off!")
                /*if (isAlwaysOn && *//*isCharging &&*//* !isAlwaysOnRunning) {
                    context?.startActivity(
                        Intent(context, AlwaysOnActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                }*/
            }
            Intent.ACTION_SCREEN_ON -> {
                isScreenOn = true
                Timber.d("onReceive: screen on!")
            }
        }
    }
}