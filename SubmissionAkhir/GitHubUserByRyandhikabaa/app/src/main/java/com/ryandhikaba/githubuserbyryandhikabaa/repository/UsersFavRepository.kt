package com.ryandhikaba.githubuserbyryandhikabaa.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ryandhikaba.githubuserbyryandhikabaa.database.UsersFavEntity
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
    fun getAllUsersFav(): LiveData<List<UsersFavEntity>> = mUsersFavDao.getAllUsersFav()

    fun getFavoriteUserByUsername(username: String): LiveData<UsersFavEntity> = mUsersFavDao.getFavoriteUserByUsername(username)

    fun insert(usersFavEntity: UsersFavEntity) {
        executorService.execute { mUsersFavDao.insert(usersFavEntity) }
    }
    fun delete(usersFavEntity: UsersFavEntity) {
        executorService.execute { mUsersFavDao.delete(usersFavEntity) }
    }
    fun update(usersFavEntity: UsersFavEntity) {
        executorService.execute { mUsersFavDao.update(usersFavEntity) }
    }
}