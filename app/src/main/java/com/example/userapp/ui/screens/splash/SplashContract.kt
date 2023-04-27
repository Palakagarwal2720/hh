package com.example.userapp.ui.screens.splash

class SplashContract {
    sealed class Effect {
        object LoadData: Effect()
        object MoveToDownTime: Effect()
    }
}