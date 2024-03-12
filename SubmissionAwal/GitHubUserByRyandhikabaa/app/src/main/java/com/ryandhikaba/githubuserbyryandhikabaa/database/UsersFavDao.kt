package com.ryandhikaba.githubuserbyryandhikabaa.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsersFavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(usersFav: UsersFav)
    @Update
    fun update(usersFav: UsersFav)
    @Delete
    fun delete(usersFav: UsersFav)
    @Query("SELECT * from usersFav ORDER BY username ASC")
    fun getAllUsersFav(): LiveData<List<UsersFav>>

    @Query("SELECT * FROM usersFav WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<UsersFav>
}