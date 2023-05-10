package com.noor.charginganimation.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.noor.charginganimation.presentation.viewpager.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.noor.charginganimation.databinding.FragmentAnimationsAndThemesBinding
import com.noor.charginganimation.presentation.fragments.animations.AnimFragment
import com.noor.charginganimation.presentation.fragments.themes.ThemeFragment
import com.noor.charginganimation.presentation.viewpager.ViewPagerAdapter

class AnimationsAndThemesFragment : Fragment() {
    private var binding: FragmentAnimationsAndThemesBinding? = null

    private var viewPager: ViewPager2? = null
    private val fragments = ArrayList<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAnimationsAndThemesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding?.viewPager
        viewPager?.setPageTransformer(ZoomOutPageTransformer())
        fragments.add(AnimFragment())
        fragments.add(ThemeFragment())

        val pagerAdapter: FragmentStateAdapter = ViewPagerAdapter(requireActivity(), fragments)
        viewPager?.adapter = pagerAdapter
        val tabLayoutMediator = TabLayoutMediator(
            binding!!.tabLayout, binding!!.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.text = "Animations"
                1 -> tab.text = "Themes"
            }
        }
        tabLayoutMediator.attach()
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        } else {
            AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
        }
    }
}