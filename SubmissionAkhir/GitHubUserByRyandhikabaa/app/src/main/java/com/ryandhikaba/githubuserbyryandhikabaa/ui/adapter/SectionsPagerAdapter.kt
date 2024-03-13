package com.ryandhikaba.githubuserbyryandhikabaa.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ryandhikaba.githubuserbyryandhikabaa.ui.fragment.TabDetailUserFragment

class SectionsPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = TabDetailUserFragment()
        fragment.arguments = Bundle().apply {
            putInt(TabDetailUserFragment.ARG_POSITION, position + 1)
            putString(TabDetailUserFragment.ARG_USERNAME, username)
        }
        return fragment
    }

}