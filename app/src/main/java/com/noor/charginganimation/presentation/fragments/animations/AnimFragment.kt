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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.noor.charginganimation.R
import com.noor.charginganimation.core.Constants.ABSTRACT
import com.noor.charginganimation.core.Constants.CARTOON
import com.noor.charginganimation.core.Constants.CHARGING
import com.noor.charginganimation.core.Constants.HEART
import com.noor.charginganimation.core.extensions.click
import com.noor.charginganimation.core.extensions.hide
import com.noor.charginganimation.core.extensions.show
import com.noor.charginganimation.core.extensions.toast
import com.noor.charginganimation.databinding.FragmentAnimBinding
import com.noor.charginganimation.domain.model.Response
import com.noor.charginganimation.presentation.fragments.AnimationsAndThemesFragmentDirections
import com.noor.charginganimation.presentation.fragments.animations.adapters.AbstractAnimationsAdapter
import com.noor.charginganimation.presentation.fragments.animations.adapters.CartoonAnimationsAdapter
import com.noor.charginganimation.presentation.fragments.animations.adapters.ChargingAnimationsAdapter
import com.noor.charginganimation.presentation.fragments.animations.adapters.HeartAnimationsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AnimFragment : Fragment() {
    private var binding: FragmentAnimBinding? = null

    private val viewModel: AnimationsViewModel by viewModels()
    private lateinit var abstractAnimationsAdapter: AbstractAnimationsAdapter
    private lateinit var cartoonAnimationsAdapter: CartoonAnimationsAdapter
    private lateinit var heartAnimationsAdapter: HeartAnimationsAdapter
    private lateinit var chargingAnimationsAdapter: ChargingAnimationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAnimBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadAbstractAnimations()
        loadCartoonAnimations()
        loadHeartAnimations()
        loadChargingAnimations()

        setupClickListeners()

        /*lifecycleScope.launch {
            viewModel.animationsResponse.collect {
                when (val getAnimationResponse = it) {
                    is Response.Loading -> binding?.progressBar?.show()
                    is Response.Success -> {
                        binding?.progressBar?.hide()
                        adapter.setData(getAnimationResponse.data)
                    }
                    is Response.Failure -> {
                        binding?.progressBar?.hide()
                        activity?.toast(getAnimationResponse.e?.message.toString())
                    }
                }
            }
        }*/
    }

    private fun setupClickListeners() {
        binding?.text?.click {
            lifecycleScope.launch {
                viewModel.addAnimation("charging1", "test url", "charging", "https://firebasestorage.googleapis.com/v0/b/charging-animation-468fd.appspot.com/o/thumbnails%2Fcharging_thumbnail.png?alt=media&token=6893d87b-ac82-4248-9ef4-bdb7e84c924d")

                when (val addAnimationResponse = viewModel.addAnimationResponse) {
                    is Response.Loading -> activity?.toast("adding animation...")
                    is Response.Success -> activity?.toast("animation added successfully!")
                    is Response.Failure -> activity?.toast(addAnimationResponse.e?.message.toString())
                }
            }
        }

        binding?.viewAllAbstract?.click {
            if (findNavController().currentDestination?.id == R.id.animationsAndThemesFragment) {
                val action =
                    AnimationsAndThemesFragmentDirections.actionAnimationsAndThemesFragmentToAllAnimationsFragment(
                        ABSTRACT
                    )
                findNavController().navigate(action)
            }
        }

        binding?.viewAllCartoons?.click {
            if (findNavController().currentDestination?.id == R.id.animationsAndThemesFragment) {
                val action =
                    AnimationsAndThemesFragmentDirections.actionAnimationsAndThemesFragmentToAllAnimationsFragment(
                        CARTOON
                    )
                findNavController().navigate(action)
            }
        }

        binding?.viewAllHearts?.click {
            if (findNavController().currentDestination?.id == R.id.animationsAndThemesFragment) {
                val action =
                    AnimationsAndThemesFragmentDirections.actionAnimationsAndThemesFragmentToAllAnimationsFragment(
                        HEART
                    )
                findNavController().navigate(action)
            }
        }

        binding?.viewAllCharging?.click {
            if (findNavController().currentDestination?.id == R.id.animationsAndThemesFragment) {
                val action =
                    AnimationsAndThemesFragmentDirections.actionAnimationsAndThemesFragmentToAllAnimationsFragment(
                        CHARGING
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun loadAbstractAnimations() {
        lifecycleScope.launch {
            abstractAnimationsAdapter = AbstractAnimationsAdapter(::onItemClick, ::onDeleteClick)
            binding?.rvAnimationsAbstract?.layoutManager = GridLayoutManager(context, 3)
            binding?.rvAnimationsAbstract?.adapter = abstractAnimationsAdapter

            viewModel.abstractAnimationsResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Loading -> binding?.progressBar?.show()
                    is Response.Success -> {
                        binding?.progressBar?.hide()
                        abstractAnimationsAdapter.setFirstThree(response.data)
                    }
                    is Response.Failure -> {
                        binding?.progressBar?.hide()
                        activity?.toast(response.e?.message.toString())
                    }
                }
            }
        }
    }

    private fun loadCartoonAnimations() {
        lifecycleScope.launch {
            cartoonAnimationsAdapter = CartoonAnimationsAdapter(::onItemClick, ::onDeleteClick)
            binding?.rvAnimationsCartoon?.layoutManager = GridLayoutManager(context, 3)
            binding?.rvAnimationsCartoon?.adapter = cartoonAnimationsAdapter

            viewModel.cartoonAnimationsResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Loading -> binding?.progressBar?.show()
                    is Response.Success -> {
                        binding?.progressBar?.hide()
                        cartoonAnimationsAdapter.setFirstThree(response.data)
                    }
                    is Response.Failure -> {
                        binding?.progressBar?.hide()
                        activity?.toast(response.e?.message.toString())
                    }
                }
            }
        }
    }

    private fun loadHeartAnimations() {
        lifecycleScope.launch {
            heartAnimationsAdapter = HeartAnimationsAdapter(::onItemClick, ::onDeleteClick)
            binding?.rvAnimationsHeart?.layoutManager = GridLayoutManager(context, 3)
            binding?.rvAnimationsHeart?.adapter = heartAnimationsAdapter

            viewModel.heartAnimationsResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Loading -> binding?.progressBar?.show()
                    is Response.Success -> {
                        binding?.progressBar?.hide()
                        heartAnimationsAdapter.setFirstThree(response.data)
                    }
                    is Response.Failure -> {
                        binding?.progressBar?.hide()
                        activity?.toast(response.e?.message.toString())
                    }
                }
            }
        }
    }

    private fun loadChargingAnimations() {
        lifecycleScope.launch {
            chargingAnimationsAdapter = ChargingAnimationsAdapter(::onItemClick, ::onDeleteClick)
            binding?.rvAnimationsCharging?.layoutManager = GridLayoutManager(context, 3)
            binding?.rvAnimationsCharging?.adapter = chargingAnimationsAdapter

            viewModel.chargingAnimationsResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Loading -> binding?.progressBar?.show()
                    is Response.Success -> {
                        binding?.progressBar?.hide()
                        chargingAnimationsAdapter.setFirstThree(response.data)
                    }
                    is Response.Failure -> {
                        binding?.progressBar?.hide()
                        activity?.toast(response.e?.message.toString())
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