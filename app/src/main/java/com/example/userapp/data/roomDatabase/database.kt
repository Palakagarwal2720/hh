package com.example.userapp.data.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.userapp.data.FavoriteUser
import com.example.userapp.data.User

@Database(entities = [User::class,FavoriteUser::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteDao():FavoriteDao
}
