package com.example.userapp.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User (

    @PrimaryKey @SerializedName("id"       ) var id       : Int?     = null,
    @SerializedName("name"     ) var name     : String?  = null,
    @SerializedName("username" ) var username : String?  = null,
    @SerializedName("email"    ) var email    : String?  = null,
    @SerializedName("phone"    ) var phone    : String?  = null,
    @SerializedName("website"  ) var website  : String?  = null,

)
@Entity
data class FavoriteUser (

    @PrimaryKey @SerializedName("id"       ) var id       : Int?     = null,
    @SerializedName("name"     ) var name     : String?  = null,
    )