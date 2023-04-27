package com.example.userapp.ui.screens.list

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.graphics.rotationMatrix
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userapp.data.FavoriteUser
import com.example.userapp.data.User
import com.example.userapp.data.di.NetworkModule
import com.example.userapp.ui.screens.splash.SplashContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel@Inject constructor():ViewModel() {
    var state by mutableStateOf(ListContract.State())
    var effects = Channel<ListContract.Effect>(Channel.UNLIMITED)
        private set
    init {
        viewModelScope.launch {
            effects.send(ListContract.Effect.LoadData)
        }
    }
    fun loadData(context:Context)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            val roomDatabase = NetworkModule.getInstanceOfRoomDataBase(context)
            val listOfUser=roomDatabase.userDao().getAll()
            if(listOfUser.isNotEmpty())
            {
                state=state.copy(
                    listOfUsers=listOfUser.asList(),
                )
            }
            loadListOfFavoriteUser(context)
        }
    }
    fun loadListOfFavoriteUser(context: Context)
    {
        viewModelScope.launch (Dispatchers.IO){
            val roomDatabase = NetworkModule.getInstanceOfRoomDataBase(context)
            val listOfFavoriteUser=roomDatabase.favoriteDao().getAll()
            if(listOfFavoriteUser.isNotEmpty())
            {
                state=state.copy(
                    listOfFavoriteUser = listOfFavoriteUser.asList()
                )
            }
        }
    }
    fun addFavoriteUser(favoriteUser: FavoriteUser,context: Context) {
        viewModelScope.launch(Dispatchers.IO)
        {
            val roomDatabase = NetworkModule.getInstanceOfRoomDataBase(context)
            if(roomDatabase.favoriteDao().getAll().contains(favoriteUser))
            {
                roomDatabase.favoriteDao().delete(favoriteUser)
                withContext(Dispatchers.Main)
                {
                    Toast.makeText(context, "${favoriteUser.name} DELETED FROM FAVORITE LIST", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            else
            {
                roomDatabase.favoriteDao().insert(favoriteUser)
                withContext(Dispatchers.Main)
                {
                    Toast.makeText(context, "${favoriteUser.name} INSERTED INTO FAVORITE LIST", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            Log.d("list of favorite user",""+roomDatabase.favoriteDao().getAll().asList())
        }
    }
}