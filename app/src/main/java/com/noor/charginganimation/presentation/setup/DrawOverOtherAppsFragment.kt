package com.noor.charginganimation.presentation.setup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noor.charginganimation.R
import com.noor.charginganimation.databinding.FragmentDrawOverOtherAppsBinding

class DrawOverOtherAppsFragment : Fragment() {
    private var binding: FragmentDrawOverOtherAppsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDrawOverOtherAppsBinding.inflate(inflater, container, false)
        return binding?.root
    }
}