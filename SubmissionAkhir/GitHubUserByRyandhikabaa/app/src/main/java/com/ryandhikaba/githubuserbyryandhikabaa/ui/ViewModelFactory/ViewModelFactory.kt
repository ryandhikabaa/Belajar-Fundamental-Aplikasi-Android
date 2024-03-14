package com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ryandhikaba.githubuserbyryandhikabaa.ui.DetailUserActivity.DetailUserViewModel
import com.ryandhikaba.githubuserbyryandhikabaa.ui.FavoriteUsersActivity.FavoriteUsersViewModel
import com.ryandhikaba.githubuserbyryandhikabaa.ui.MainAcivity.MainViewModel
import com.ryandhikaba.githubuserbyryandhikabaa.ui.SplashScreen.SplashScreenViewModel
import com.ryandhikaba.githubuserbyryandhikabaa.utils.SettingPreferences

class ViewModelFactory (private val mApplication: Application, private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {


    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application, pref: SettingPreferences): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, pref)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUsersViewModel::class.java)) {
            return FavoriteUsersViewModel(mApplication) as T
        }
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(mApplication) as T
        }
        if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
            return SplashScreenViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}