package com.ryandhikaba.githubuserbyryandhikabaa.ui.SplashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ryandhikaba.githubuserbyryandhikabaa.utils.SettingPreferences

class SplashScreenViewModel (private val pref: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}