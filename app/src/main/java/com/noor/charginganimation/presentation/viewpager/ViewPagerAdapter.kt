package com.noor.charginganimation.presentation.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fa: FragmentActivity?,
    private val fragmentList: ArrayList<Fragment>
) :
    FragmentStateAdapter(fa!!) {
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }
}