package com.ryandhikaba.githubuserbyryandhikabaa.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ryandhikaba.githubuserbyryandhikabaa.R
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivityDetailUserBinding
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivityMainBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        private const val TAG = "RBA:MainActivity ||  "
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){

        }
    }
}