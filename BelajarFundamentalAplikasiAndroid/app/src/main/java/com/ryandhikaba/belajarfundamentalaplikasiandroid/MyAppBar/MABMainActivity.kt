package com.ryandhikaba.belajarfundamentalaplikasiandroid.MyAppBar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ryandhikaba.belajarfundamentalaplikasiandroid.R
import com.ryandhikaba.belajarfundamentalaplikasiandroid.databinding.ActivityMabmainBinding
import com.ryandhikaba.belajarfundamentalaplikasiandroid.databinding.ActivityMainBinding

class MABMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMabmainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMabmainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MABMenuFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.menu2 -> {
                    val intent = Intent(this, MABMenuActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}