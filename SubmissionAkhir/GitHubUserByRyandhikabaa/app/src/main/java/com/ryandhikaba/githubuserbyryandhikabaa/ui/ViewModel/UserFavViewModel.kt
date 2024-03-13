package com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ryandhikaba.githubuserbyryandhikabaa.database.UsersFav
import com.ryandhikaba.githubuserbyryandhikabaa.repository.UsersFavRepository

class UserFavViewModel (application: Application) : ViewModel() {
    private val mUsersFavRepository: UsersFavRepository = UsersFavRepository(application)

    fun insert(usersFav: UsersFav) {
        mUsersFavRepository.insert(usersFav)
    }
    fun update(usersFav: UsersFav) {
        mUsersFavRepository.update(usersFav)
    }
    fun delete(usersFav: UsersFav) {
        mUsersFavRepository.delete(usersFav)
    }

    fun getAllUsersFav(): LiveData<List<UsersFav>> = mUsersFavRepository.getAllUsersFav()
    fun getFavoriteUserByUsername(username: String): LiveData<UsersFav> {
        return mUsersFavRepository.getFavoriteUserByUsername(username)
    }

    fun toggleFavorite(userFavOnClick: UsersFav) {
        val username = userFavOnClick.username ?: return
        val existingUser = getFavoriteUserByUsername(username).value

//        Log.d("RBA", "toggleFavorite: $existingUser" )
        if (existingUser == null) {
            insert(userFavOnClick)
        } else {
            delete(userFavOnClick)
        }
    }


}