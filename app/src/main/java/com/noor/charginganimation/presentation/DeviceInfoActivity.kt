package com.noor.charginganimation.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noor.charginganimation.databinding.ActivityDeviceInfoBinding

class DeviceInfoActivity : AppCompatActivity() {
    private val binding: ActivityDeviceInfoBinding by lazy {
        ActivityDeviceInfoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val stringBuilder = StringBuilder()

        stringBuilder.append("OS Version: ${System.getProperty("os.version")}\n") // OS version
        stringBuilder.append("API level: ${android.os.Build.VERSION.SDK}\n")      // API Level
        stringBuilder.append("Device: ${android.os.Build.DEVICE}\n")           // Device
        stringBuilder.append("Model: ${android.os.Build.MODEL}\n")            // Model
        stringBuilder.append("Product: ${android.os.Build.PRODUCT}\n")
        stringBuilder.append("Fingerprint : ${android.os.Build.FINGERPRINT}\n")
        stringBuilder.append("Brand : ${android.os.Build.BRAND}\n")
        stringBuilder.append("Manufacturer : ${android.os.Build.MANUFACTURER}\n")
        stringBuilder.append("Type : ${android.os.Build.TYPE}\n")
        stringBuilder.append("Board : ${android.os.Build.BOARD}\n")
        stringBuilder.append("Bootloader : ${android.os.Build.BOOTLOADER}\n")
        stringBuilder.append("Display : ${android.os.Build.DISPLAY}\n")
        stringBuilder.append("Hardware : ${android.os.Build.HARDWARE}\n")
        stringBuilder.append("Id : ${android.os.Build.ID}\n")

        binding.textView2.text = stringBuilder.toString()
    }
}