package com.example.userapp.ui.screens.list

import com.example.userapp.data.FavoriteUser
import com.example.userapp.data.User

class ListContract {
    data class State(
        val isLoading:Boolean=false,
        val listOfUsers:List<User>?=null,
        val listOfFavoriteUser:List<FavoriteUser>?=null
    )
    sealed class Effect {
        object LoadData: Effect()
        object MoveToDownTime: Effect()
    }
}