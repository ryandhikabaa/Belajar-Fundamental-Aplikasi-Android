package com.ryandhikaba.githubuserbyryandhikabaa.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ryandhikaba.githubuserbyryandhikabaa.database.UsersFav
import com.ryandhikaba.githubuserbyryandhikabaa.database.UsersFavDao
import com.ryandhikaba.githubuserbyryandhikabaa.database.UsersFavDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UsersFavRepository(application: Application) {
    private val mUsersFavDao: UsersFavDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = UsersFavDatabase.getDatabase(application)
        mUsersFavDao = db.userFavDao()
    }
    fun getAllUsersFav(): LiveData<List<UsersFav>> = mUsersFavDao.getAllUsersFav()

    fun getFavoriteUserByUsername(username: String): LiveData<UsersFav> = mUsersFavDao.getFavoriteUserByUsername(username)

    fun insert(usersFav: UsersFav) {
        executorService.execute { mUsersFavDao.insert(usersFav) }
    }
    fun delete(usersFav: UsersFav) {
        executorService.execute { mUsersFavDao.delete(usersFav) }
    }
    fun update(usersFav: UsersFav) {
        executorService.execute { mUsersFavDao.update(usersFav) }
    }
}