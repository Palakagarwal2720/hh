package com.example.userapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.userapp.R
import com.example.userapp.data.User
import com.example.userapp.ui.screens.SplashScreen
import com.example.userapp.ui.screens.detail.DetailScreen
import com.example.userapp.ui.screens.list.ListScreen
import com.example.userapp.ui.screens.list.ListViewModel
import com.example.userapp.ui.screens.splash.SplashViewModel
import com.example.userapp.util.ConnectionState
import com.example.userapp.util.DownTimeScreen
import com.example.userapp.util.connectivityState
import com.google.gson.Gson


@Composable
fun UserAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.SPLASH_SCREEN
) {
    val context= LocalContext.current
    Column()
    {
        val connection by connectivityState()
        val isConnected = connection === ConnectionState.Available
        if (!isConnected) {
            Box(modifier= modifier
                .fillMaxWidth()
                .background(Color.Red))
            {
                Text(text = context.getString(R.string.no_internet_connection))
            }
        }
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Routes.SPLASH_SCREEN) {
                val viewModel: SplashViewModel = hiltViewModel()
                SplashScreen(navHostController = navController,viewModel=viewModel)
            }
            composable(Routes.DOWN_TIME_SCREEN)
            {
                DownTimeScreen()
            }
            composable(Routes.LIST_SCREEN)
            {
                val viewModel:ListViewModel= hiltViewModel()
                ListScreen(navHostController = navController, viewModel = viewModel)
            }
            composable("${Routes.DETAIL_SCREEN}?user={user}",arguments = listOf(navArgument("loanDetail") {
                type = NavType.StringType
                defaultValue = Gson().toJson(User())
            }))
            {
                DetailScreen(navHostController = navController, user =Gson().fromJson(
                    it.arguments?.getString("user"),
                    User::class.java
                ),)
            }
        }
    }
}