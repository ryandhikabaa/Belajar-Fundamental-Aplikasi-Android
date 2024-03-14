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
    fun insert(usersFavEntity: UsersFavEntity)
    @Update
    fun update(usersFavEntity: UsersFavEntity)
    @Delete
    fun delete(usersFavEntity: UsersFavEntity)
    @Query("SELECT * from UsersFavEntity ORDER BY username ASC")
    fun getAllUsersFav(): LiveData<List<UsersFavEntity>>

    @Query("SELECT * FROM UsersFavEntity WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<UsersFavEntity>
}