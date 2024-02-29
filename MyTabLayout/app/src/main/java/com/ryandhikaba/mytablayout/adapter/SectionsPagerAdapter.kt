package com.ryandhikaba.mytablayout.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ryandhikaba.mytablayout.fragment.HomeFragment
import com.ryandhikaba.mytablayout.fragment.ProfileFragment

class SectionsPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity)  {
    override fun getItemCount(): Int {
//        return 2
        return 3
    }

    override fun createFragment(position: Int): Fragment {
//        var fragment: Fragment? = null
//        when (position) {
//            0 -> fragment = HomeFragment()
//            1 -> fragment = ProfileFragment()
//        }
//        return fragment as Fragment

        val fragment = HomeFragment()
        fragment.arguments = Bundle().apply {
            putInt(HomeFragment.ARG_SECTION_NUMBER, position + 1)
        }
        return fragment
    }
}