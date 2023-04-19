package com.noor.charginganimation.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.noor.charginganimation.databinding.FragmentDeviceInfoBinding

class DeviceInfoFragment : Fragment() {
    private var binding: FragmentDeviceInfoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDeviceInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        binding?.textView?.text = stringBuilder.toString()
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        } else {
            AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
        }
    }
}