package com.example.userapp.ui.screens.list

import android.widget.TableLayout
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.userapp.R
import com.example.userapp.data.FavoriteUser
import com.example.userapp.navigation.Routes
import com.example.userapp.ui.screens.splash.SplashContract
import com.example.userapp.ui.theme.DarkViolet
import com.example.userapp.ui.theme.OsloGrey
import com.example.userapp.ui.theme.UserTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun ListScreen(navHostController: NavHostController,viewModel: ListViewModel) {
    val context = LocalContext.current
    LaunchedEffect(viewModel.effects.receiveAsFlow()) {
        viewModel.effects.receiveAsFlow().onEach { effect ->
            when (effect) {
                is ListContract.Effect.LoadData -> {
                    viewModel.loadData(context = context)
                }
                is ListContract.Effect.MoveToDownTime -> {
                    navHostController.navigate(Routes.DOWN_TIME_SCREEN)
                }
            }
        }.collect()
    }
    val pagerState = rememberPagerState(pageCount = 2)
    LaunchedEffect(key1 = pagerState.currentPage, block = {
        if(pagerState.currentPage==1)
        {
            viewModel.loadListOfFavoriteUser(context)
        }
    })


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        stickyHeader {
            TabLayout(viewModel = viewModel, pagerState = pagerState )
        }
        item {
            HorizontalPager(state = pagerState)
            {
                if (pagerState.currentPage == 0) {
                    Column()
                    {
                        viewModel.state.listOfUsers?.forEach{
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(UserTheme.dimens.x_16_dp)
                                    .clickable {
                                        viewModel.addFavoriteUser(
                                            favoriteUser = FavoriteUser(
                                                id = it.id,
                                                name = it.name
                                            ), context = context
                                        )
                                    },
                                border = BorderStroke(
                                    width = UserTheme.dimens.x_1_dp,
                                    color = OsloGrey
                                )
                            )
                            {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = UserTheme.dimens.x_12_dp,
                                        vertical = UserTheme.dimens.x_20_dp
                                    ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                )
                                {
                                    Text(text = it.name ?: "")
                                    Image(
                                        modifier = Modifier.clickable {
                                            navHostController.navigate(
                                                "${Routes.DETAIL_SCREEN}?user=${
                                                    Gson().toJson(
                                                        it
                                                    )
                                                }"
                                            )
                                        },
                                        painter = painterResource(id = R.drawable.ic_chevron_right),
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    }
                }
                else
                {
                    Column()
                    {
                        viewModel.state.listOfFavoriteUser?.forEach{
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(UserTheme.dimens.x_16_dp),
                                border = BorderStroke(
                                    width = UserTheme.dimens.x_1_dp,
                                    color = OsloGrey
                                )
                            )
                            {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = UserTheme.dimens.x_12_dp,
                                        vertical = UserTheme.dimens.x_20_dp
                                    ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                )
                                {
                                    Text(text = it.name ?: "")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(viewModel: ListViewModel,pagerState: PagerState)
{
    val listOfTabs = listOf(
        "User","Favorite User"
    )
    val scope= rememberCoroutineScope()
    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = UserTheme.dimens.x_20_dp),
        backgroundColor = Color.White,
        selectedTabIndex = pagerState.currentPage,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
        indicator = {
        },
    ) {
        listOfTabs.forEachIndexed { index, title ->
            Column{
                Tab(text = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(UserTheme.dimens.x_16_dp),
                            horizontalArrangement = Arrangement.Start
                        )
                        {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                index
                            )
                        }
                    },
                    selectedContentColor = DarkViolet,
                    unselectedContentColor = Color.Black
                )
                if(pagerState.currentPage==index) {
                    Divider(color = DarkViolet)
                }
            }
        }
    }

}