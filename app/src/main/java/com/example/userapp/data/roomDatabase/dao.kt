package com.example.userapp.data.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.userapp.data.FavoriteUser
import com.example.userapp.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Array<User>
    @Insert
    fun insert(vararg user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun deleteAll()
}
@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favoriteUser")
    fun getAll(): Array<FavoriteUser>
    @Insert
    fun insert(vararg favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("DELETE FROM favoriteUser")
    fun deleteAll()
}

