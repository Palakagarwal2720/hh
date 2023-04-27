package com.example.userapp.data

import retrofit2.Response
import retrofit2.http.GET

interface UserApi {
    @GET("users")
    suspend fun getUserDetail() : Response<Array<User>>
}