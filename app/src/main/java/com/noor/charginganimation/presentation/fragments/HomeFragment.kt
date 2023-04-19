package com.noor.charginganimation.presentation.fragments

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

        val level = activity?.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            ?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            ?: 0
        binding?.tvBatteryStatus?.text = resources.getString(R.string.charged_percent, level)
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
}