package com.chd.mimitogether.ui.party.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
    val fragments = mutableListOf<Fragment>()

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
    override fun getItemCount():Int {
        return fragments.size
    }
}