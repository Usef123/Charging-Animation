package com.noor.charginganimation.presentation.fragments

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.registerReceiver
import com.noor.charginganimation.R
import com.noor.charginganimation.core.extensions.click
import com.noor.charginganimation.core.extensions.toast
import com.noor.charginganimation.core.utils.PrefUtils.isAlwaysOn
import com.noor.charginganimation.databinding.FragmentHomeBinding
import com.noor.charginganimation.presentation.BatteryInfoActivity
import com.noor.charginganimation.presentation.DeviceInfoActivity

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
            startActivity(Intent(requireContext(), DeviceInfoActivity::class.java))
        }

        binding?.btnBatteryInfo?.click {
            startActivity(Intent(requireContext(), BatteryInfoActivity::class.java))
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