package com.ryandhikaba.belajarfundamentalaplikasiandroid.MyFlexibleFragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ryandhikaba.belajarfundamentalaplikasiandroid.MyFlexibleFragment.fragment.HomeFragment
import com.ryandhikaba.belajarfundamentalaplikasiandroid.R
import com.ryandhikaba.belajarfundamentalaplikasiandroid.databinding.ActivityMainBinding

class MainFlexibleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_flexible)

        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragment()
        val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)
        if (fragment !is HomeFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + HomeFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }
    }
}