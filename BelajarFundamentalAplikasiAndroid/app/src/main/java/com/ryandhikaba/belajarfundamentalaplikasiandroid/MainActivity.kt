package com.ryandhikaba.belajarfundamentalaplikasiandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ryandhikaba.belajarfundamentalaplikasiandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvWelcome.text = "Hello Dicoding"
    }
}