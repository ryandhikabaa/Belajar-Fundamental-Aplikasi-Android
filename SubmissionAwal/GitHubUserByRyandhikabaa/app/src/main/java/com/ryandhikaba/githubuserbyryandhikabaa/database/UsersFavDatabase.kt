package com.ryandhikaba.githubuserbyryandhikabaa.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context


@Database(entities = [UsersFav::class], version = 1)
abstract class UsersFavDatabase : RoomDatabase() {
    abstract fun userFavDao(): UsersFavDao
    companion object {
        @Volatile
        private var INSTANCE: UsersFavDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): UsersFavDatabase {
            if (INSTANCE == null) {
                synchronized(UsersFavDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UsersFavDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as UsersFavDatabase
        }
    }
}