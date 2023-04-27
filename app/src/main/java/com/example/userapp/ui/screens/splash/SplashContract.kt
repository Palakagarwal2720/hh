package com.example.userapp.ui.screens.splash

class SplashContract {
    data class State(
        val isLoading:Boolean=false
    )
    sealed class Effect {
        object LoadData: Effect()
        object MoveToDownTime: Effect()
    }
}