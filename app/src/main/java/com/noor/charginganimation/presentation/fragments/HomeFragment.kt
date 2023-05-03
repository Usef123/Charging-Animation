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
import androidx.navigation.fragment.findNavController
import com.noor.charginganimation.R
import com.noor.charginganimation.core.extensions.click
import com.noor.charginganimation.core.extensions.toast
import com.noor.charginganimation.core.utils.PrefUtils.isAlwaysOn
import com.noor.charginganimation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

    private var intentFilter: IntentFilter? = null
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            updateBatteryData(intent)
        }
    }

    private fun updateBatteryData(intent: Intent) {
        val present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false)

        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        if (level != -1) {
            binding?.tvBatteryStatus?.text = resources.getString(R.string.charged_percent, level)
        }

        if (present) {
            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)

            when (status) {
                BatteryManager.BATTERY_STATUS_CHARGING -> {
                    binding?.animationView?.setAnimation(R.raw.charging)
                }

                BatteryManager.BATTERY_STATUS_DISCHARGING -> {
                    binding?.animationView?.cancelAnimation()
                }

                BatteryManager.BATTERY_STATUS_FULL -> {
                    binding?.animationView?.cancelAnimation()
                }

                BatteryManager.BATTERY_STATUS_UNKNOWN -> {
                    binding?.animationView?.cancelAnimation()
                }

                BatteryManager.BATTERY_STATUS_NOT_CHARGING -> {
                    binding?.animationView?.cancelAnimation()
                }
                else -> {
                    binding?.animationView?.cancelAnimation()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()

        intentFilter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        intentFilter = IntentFilter(Intent.ACTION_POWER_DISCONNECTED)
        intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        activity?.registerReceiver(broadcastReceiver, intentFilter)

//        binding?.animationView?.setAnimation(R.raw.charging)
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        } else {
            AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
        }
    }

    private fun setupListeners() {
        binding?.switchAOD?.setOnCheckedChangeListener { _, isChecked ->
            isAlwaysOn = isChecked
        }

        binding?.btnBatteryAnim?.click {
            activity?.toast("not implemented!")
        }

        binding?.btnThemes?.click {
            activity?.toast("not implemented!")
        }

        binding?.btnAlarm?.click {
            activity?.toast("not implemented!")
        }

        binding?.btnDeviceInfo?.click {
            findNavController().navigate(R.id.action_homeFragment_to_deviceInfoFragment)
        }

        binding?.btnBatteryInfo?.click {
            findNavController().navigate(R.id.action_homeFragment_to_batteryInfoFragment)
        }

        binding?.btnGuide?.click {
            activity?.toast("not implemented!")
        }
    }

    override fun onStart() {
        binding?.switchAOD?.isChecked = isAlwaysOn
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(broadcastReceiver)
    }
}