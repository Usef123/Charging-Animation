package com.noor.charginganimation.presentation.fragments

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.noor.charginganimation.databinding.FragmentDeviceInfoBinding
import timber.log.Timber
import java.io.File
import kotlin.math.ceil


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

        val device = android.os.Build.DEVICE
        val brand = android.os.Build.BRAND
        val deviceId = android.os.Build.ID
        val model = android.os.Build.MODEL
        val manufacturer = android.os.Build.MANUFACTURER
        val type = android.os.Build.TYPE
        val sdk = android.os.Build.VERSION.SDK
        val cpu = android.os.Build.CPU_ABI
        val memory = getDeviceMemoryInGB()
//        val storage = getDeviceStorage()
        val osVersion = System.getProperty("os.version")
        val fingerPrint = android.os.Build.FINGERPRINT
        val display = android.os.Build.DISPLAY

        binding?.tvDevice?.text = "$brand $device"

        binding?.tvBrandValue?.text = brand
        binding?.tvDeviceId?.text = deviceId
        binding?.tvModel?.text = model
        binding?.tvSdk?.text = sdk
        binding?.tvCpu?.text = cpu
        binding?.tvRam?.text = "$memory"
        binding?.tvFingerprint?.text = fingerPrint
        binding?.tvDisplay?.text = display

        val stringBuilder = StringBuilder()

        stringBuilder.append("OS Version: $osVersion\n") // OS version
        stringBuilder.append("API level: $sdk\n")      // API Level
        stringBuilder.append("Device: $device\n")           // Device
        stringBuilder.append("Model: $model\n")            // Model
        stringBuilder.append("Product: ${android.os.Build.PRODUCT}\n")
        stringBuilder.append("Fingerprint : $fingerPrint\n")
        stringBuilder.append("Brand : $brand\n")
        stringBuilder.append("Manufacturer : $manufacturer\n")
        stringBuilder.append("Type : $type\n")
        stringBuilder.append("Board : ${android.os.Build.BOARD}\n")
        stringBuilder.append("Bootloader : ${android.os.Build.BOOTLOADER}\n")
        stringBuilder.append("Display : ${android.os.Build.DISPLAY}\n")
        stringBuilder.append("Hardware : ${android.os.Build.HARDWARE}\n")
        stringBuilder.append("Id : ${android.os.Build.ID}\n")
        stringBuilder.append("CPU : $cpu\n")
        stringBuilder.append("Memory : $memory GB\n")
        stringBuilder.append("System Storage : ${getFreeSystemMemory()}\n")
        stringBuilder.append("Device Storage : ${getDeviceStorage()}\n")
        stringBuilder.append("Total Storage : ${getFreeSystemMemory() + getDeviceStorage()} \n")

        Timber.d("Device Info: $stringBuilder")
    }

    private fun getDeviceMemoryInGB(): Int {
        val actManager = activity?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        return ceil((memInfo.totalMem) / (1024f * 1024f * 1024f)).toInt()
    }

    private fun getDeviceStorage(): Long {
//        val iStat = StatFs(Environment.getDataDirectory().absolutePath)
//        val iTotalSpace = (iStat.blockSizeLong * iStat.blockCountLong)
//        val iTotalSpace = iStat.availableBlocksLong
        val iTotalSpace = getFreeInternalMemory() + getFreeSystemMemory()
        return iTotalSpace / (1024 * 1024)
    }

    // Get Android OS (system partition) free space
    private fun getFreeSystemMemory(): Long {
        return getFreeMemory(Environment.getRootDirectory())
    }

    // Get internal (data partition) free space
    // This will match what's shown in System Settings > Storage for
    // Internal Space, when you subtract Total - Used
    private fun getFreeInternalMemory(): Long {
        return getFreeMemory(Environment.getDataDirectory())
    }

    private fun getFreeMemory(path: File): Long {
        val stats = StatFs(path.absolutePath)
        return stats.availableBlocksLong * stats.blockSizeLong
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        } else {
            AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
        }
    }
}