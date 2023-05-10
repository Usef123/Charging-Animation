package com.noor.charginganimation.presentation.fragments.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.noor.charginganimation.R
import com.noor.charginganimation.core.Constants.ABSTRACT
import com.noor.charginganimation.core.Constants.CARTOON
import com.noor.charginganimation.core.Constants.CHARGING
import com.noor.charginganimation.core.Constants.HEART
import com.noor.charginganimation.core.extensions.hide
import com.noor.charginganimation.core.extensions.show
import com.noor.charginganimation.core.extensions.toast
import com.noor.charginganimation.databinding.FragmentAllAnimationsBinding
import com.noor.charginganimation.domain.model.Response
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AllAnimationsFragment : Fragment() {
    private var binding: FragmentAllAnimationsBinding? = null

    private val viewModel: AnimationsViewModel by viewModels()
    private lateinit var adapter: AnimationsAdapter

    private val args: AllAnimationsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllAnimationsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AnimationsAdapter(::onItemClick, ::onDeleteClick)
        binding?.rvAnimations?.layoutManager = GridLayoutManager(context, 3)
        binding?.rvAnimations?.adapter = adapter

        when (args.category) {
            ABSTRACT -> {
                binding?.tvTitle?.text = getString(R.string.abstract_animations)
                loadAnimations(ABSTRACT)
            }
            CARTOON -> {
                binding?.tvTitle?.text = getString(R.string.cartoon_animations)
                loadAnimations(CARTOON)
            }
            HEART -> {
                binding?.tvTitle?.text = getString(R.string.heart_animations)
                loadAnimations(HEART)
            }
            CHARGING -> {
                binding?.tvTitle?.text = getString(R.string.charging_animations)
                loadAnimations(CHARGING)
            }
        }

    }

    private fun loadAnimations(category: String) {
        lifecycleScope.launch {
            viewModel.getAnimationsByCategory(category).collect {
                when (val getAnimationResponse = it) {
                    is Response.Loading -> binding?.progressBar?.show()
                    is Response.Success -> {
                        binding?.progressBar?.hide()
                        adapter.setData(getAnimationResponse.data)
                        Timber.d("animations: ${getAnimationResponse.data}")
                    }

                    is Response.Failure -> {
                        binding?.progressBar?.hide()
                        activity?.toast(getAnimationResponse.e?.message.toString())
                    }
                }
            }
        }
    }

    private fun onDeleteClick(animation: com.noor.charginganimation.domain.model.Animation) {
        lifecycleScope.launch {
            viewModel.deleteAnimation(animation.id.toString())
            activity?.toast("Delete successfully!")
        }
    }

    private fun onItemClick(animation: com.noor.charginganimation.domain.model.Animation) {
        Timber.d("anim: $animation")
        activity?.toast(animation.toString())
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        } else {
            AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
        }
    }
}