package com.example.userapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.userapp.navigation.Routes
import com.example.userapp.ui.screens.splash.SplashContract
import com.example.userapp.ui.screens.splash.SplashViewModel
import com.example.userapp.ui.theme.UserTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun SplashScreen(navHostController: NavHostController,viewModel: SplashViewModel)
{
    val context= LocalContext.current
    LaunchedEffect(viewModel.effects.receiveAsFlow()) {
        viewModel.effects.receiveAsFlow().onEach { effect ->
            when (effect) {
                is SplashContract.Effect.LoadData -> {
                    viewModel.loadData(context = context,navHostController=navHostController)
                }
                is SplashContract.Effect.MoveToDownTime->{
                    navHostController.navigate(Routes.DOWN_TIME_SCREEN)
                }
            }
        }.collect()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        CircularProgressIndicator(modifier=Modifier.size(UserTheme.dimens.x_50_dp))
        Spacer(Modifier.height(UserTheme.dimens.x_16_dp))
        Text("Loading...")
    }
}