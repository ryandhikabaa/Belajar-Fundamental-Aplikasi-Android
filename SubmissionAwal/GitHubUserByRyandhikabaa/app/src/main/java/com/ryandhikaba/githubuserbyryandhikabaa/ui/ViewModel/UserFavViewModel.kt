package com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModel

import android.app.Application
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
    fun getFavoriteUserByUsername(username: String): LiveData<UsersFav> = mUsersFavRepository.getFavoriteUserByUsername(username)

    fun toggleFavorite(userFav: UsersFav) {
        val username = userFav.username ?: return
        val existingUser = mUsersFavRepository.getFavoriteUserByUsername(username).value
        if (existingUser == null) {
            insert(userFav)
        } else {
            delete(userFav)
        }
    }


}