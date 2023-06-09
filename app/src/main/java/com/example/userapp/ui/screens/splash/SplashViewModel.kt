package com.example.userapp.ui.screens.splash

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.graphics.rotationMatrix
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.userapp.data.User
import com.example.userapp.data.UserApi
import com.example.userapp.data.di.NetworkModule
import com.example.userapp.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel@Inject constructor():ViewModel(),LifecycleObserver {
    var effects = Channel<SplashContract.Effect>(Channel.UNLIMITED)
        private set
    init {
        viewModelScope.launch {
            effects.send(SplashContract.Effect.LoadData)
        }
    }
    /**
     *  To load data from network call if not present in database.
     */
    fun loadData(context: Context,navHostController: NavHostController)
    {
        val userApi = NetworkModule.getInstance().create(UserApi::class.java)
        viewModelScope.launch(Dispatchers.IO) {
            val roomDatabase = NetworkModule.getInstanceOfRoomDataBase(context)
            if (roomDatabase.userDao().getAll().isEmpty()) {
                val result = userApi.getUserDetail()
                if (result.isSuccessful) {
                    result.let {
                            if (roomDatabase.userDao().getAll().isEmpty()) {
                                for (i in it.body()!!.iterator()) {
                                    roomDatabase.userDao().insert(i)
                                }
                            }
                    }
                } else {
                    effects.send(SplashContract.Effect.MoveToDownTime)
                }
            }
            delay(1000)
            withContext(Dispatchers.Main)
            {
                navHostController.navigate(Routes.LIST_SCREEN)
            }
        }
    }

}