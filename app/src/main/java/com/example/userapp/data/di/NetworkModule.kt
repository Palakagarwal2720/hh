package com.example.userapp.data.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.userapp.data.roomDatabase.AppDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getInstanceOfRoomDataBase(context: Context): AppDatabase {
       return Room.databaseBuilder(context,
            AppDatabase::class.java, "user-database"
        ).build()
    }
}