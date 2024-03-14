package com.ryandhikaba.githubuserbyryandhikabaa.ui.FavoriteUsersActivity

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ryandhikaba.githubuserbyryandhikabaa.database.UsersFavEntity
import com.ryandhikaba.githubuserbyryandhikabaa.repository.UsersFavRepository

class FavoriteUsersViewModel (application: Application) : ViewModel() {
    private val mUsersFavRepository: UsersFavRepository = UsersFavRepository(application)

    fun getAllUsersFav(): LiveData<List<UsersFavEntity>> = mUsersFavRepository.getAllUsersFav()

}