package com.example.userapp.ui.screens.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.userapp.R
import com.example.userapp.data.User
import com.example.userapp.ui.theme.DarkViolet
import com.example.userapp.ui.theme.OsloGrey
import com.example.userapp.ui.theme.UserTheme

@Composable
fun DetailScreen(navHostController: NavHostController,user: User) {
    Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween)
    {
        Column(
            modifier = Modifier
                .padding(UserTheme.dimens.x_20_dp)
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = UserTheme.dimens.x_16_dp),
                contentAlignment = Alignment.TopCenter
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = ""
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = UserTheme.dimens.x_24_dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Column(
                    modifier = Modifier.weight(1f)
                )
                {
                    Text(
                        text = user.name ?: "",
                        style = MaterialTheme.typography.h6,
                        color = Color.Black
                    )
                    Text(
                        text = user.username ?: "",
                        style = MaterialTheme.typography.subtitle2,
                        color = OsloGrey
                    )
                }
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UserTheme.dimens.x_20_dp),
            onClick = { navHostController.popBackStack() },
            contentPadding = PaddingValues(UserTheme.dimens.x_16_dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DarkViolet,
                contentColor = Color.White
            )
        ) {
            Text("BACK", style = MaterialTheme.typography.button)
        }
    }
}