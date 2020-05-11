package com.syehan.pivuzz

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> FragmentGlobal()
            1 -> FragmentLocal()
            else -> FragmentGlobal()
        }
    }

    override fun getCount(): Int {
        return 2
    }
}