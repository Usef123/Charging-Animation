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
import com.noor.charginganimation.core.extensions.hide
import com.noor.charginganimation.core.extensions.show
import com.noor.charginganimation.core.extensions.toast
import com.noor.charginganimation.databinding.FragmentAnimBinding
import com.noor.charginganimation.domain.model.Response
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AnimFragment : Fragment() {
    private var binding: FragmentAnimBinding? = null

    private val viewModel: AnimationsViewModel by viewModels()
    private lateinit var adapter: AnimationsAdapter

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

        adapter = AnimationsAdapter(::onItemClick/*, ::onDeleteClick*/)
        binding?.rvAnimations?.adapter = adapter

        lifecycleScope.launch {
            viewModel.getAnimationsByCategory("abstract").collect {
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
        }

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

        /*binding?.text?.click {
            lifecycleScope.launch {
                viewModel.addAnimation("test name", "test url", "test cat")

                when (val addAnimationResponse = viewModel.addAnimationResponse) {
                    is Response.Loading -> activity?.toast("adding animation...")
                    is Response.Success -> activity?.toast("animation added successfully!")
                    is Response.Failure -> activity?.toast(addAnimationResponse.e?.message.toString())
                }
            }
        }*/

        /*lifecycleScope.launch {
            viewModel.deleteAnimation("id")
        }*/

    }

    /*private fun onDeleteClick(animation: com.noor.charginganimation.domain.model.Animation) {
        lifecycleScope.launch {
            viewModel.deleteAnimation(animation.id.toString())
            activity?.toast("Delete successfully!")
        }
    }*/

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