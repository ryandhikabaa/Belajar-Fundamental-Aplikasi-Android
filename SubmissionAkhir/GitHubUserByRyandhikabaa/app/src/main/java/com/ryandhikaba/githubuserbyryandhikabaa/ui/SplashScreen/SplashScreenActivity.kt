package com.ryandhikaba.githubuserbyryandhikabaa.ui.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ryandhikaba.githubuserbyryandhikabaa.R
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivityMainBinding
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivitySplashScreenBinding
import com.ryandhikaba.githubuserbyryandhikabaa.ui.FavoriteUsersActivity.FavoriteUsersViewModel
import com.ryandhikaba.githubuserbyryandhikabaa.ui.MainAcivity.MainActivity
import com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModelFactory.ViewModelFactory
import com.ryandhikaba.githubuserbyryandhikabaa.utils.SettingPreferences
import com.ryandhikaba.githubuserbyryandhikabaa.utils.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private lateinit var splashScreenViewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashScreenViewModel = obtainViewModel(this@SplashScreenActivity)
        splashScreenViewModel.getThemeSettings().observe(this@SplashScreenActivity) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding){

            val fadeIn = AnimationUtils.loadAnimation(this@SplashScreenActivity, R.anim.fade_in)

            imageView.startAnimation(fadeIn)

            lifecycleScope.launch {
                delay(2000)
                withContext(Dispatchers.Main) {
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): SplashScreenViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, SettingPreferences.getInstance(application.dataStore))
        return ViewModelProvider(activity, factory).get(SplashScreenViewModel::class.java)
    }
}