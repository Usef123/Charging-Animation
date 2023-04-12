package com.noor.charginganimation.presentation.setup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noor.charginganimation.R
import com.noor.charginganimation.databinding.FragmentPhoneStateBinding

class PhoneStateFragment : Fragment() {
    private var binding: FragmentPhoneStateBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhoneStateBinding.inflate(inflater, container, false)
        return binding?.root
    }
}