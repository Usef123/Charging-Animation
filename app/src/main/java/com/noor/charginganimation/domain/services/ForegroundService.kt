package com.noor.charginganimation.domain.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.noor.charginganimation.core.Constants.NOTIFICATION_CHANNEL_ID
import com.noor.charginganimation.core.Constants.NOTIFICATION_CHANNEL_NAME
import com.noor.charginganimation.core.Constants.NOTIFICATION_ID
import com.noor.charginganimation.receivers.CombinedServiceReceiver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForegroundService: Service() {

    private val combinedServiceReceiver = CombinedServiceReceiver()

    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder

    override fun onCreate() {
        super.onCreate()

        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_SCREEN_ON)
        registerReceiver(combinedServiceReceiver, filter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, baseNotificationBuilder.build())
        return START_REDELIVER_INTENT
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                IMPORTANCE_LOW
            )
            channel.setShowBadge(false)
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(combinedServiceReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}