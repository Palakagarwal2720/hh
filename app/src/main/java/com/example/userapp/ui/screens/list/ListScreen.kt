package com.example.userapp.ui.screens.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.userapp.R
import com.example.userapp.data.FavoriteUser
import com.example.userapp.navigation.Routes
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
    var search by remember {
        mutableStateOf("")
    }
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
        if (pagerState.currentPage == 1) {
            viewModel.loadListOfFavoriteUser(context)
        }
    })
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        stickyHeader {
            TabLayout(pagerState = pagerState)
            TextField(
                modifier = Modifier
                    .padding(UserTheme.dimens.x_16_dp)
                    .fillMaxWidth(),
                value = search,
                onValueChange = { search = it },
                trailingIcon = {
                    if (search.isEmpty()) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = ""
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_cancel),
                            contentDescription = ""
                        )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = Color.White,
                    cursorColor = OsloGrey,
                    focusedIndicatorColor = DarkViolet,
                )
            )
        }
        item {
            HorizontalPager(state = pagerState)
            {
                if (pagerState.currentPage == 0) {
                    UserScreen(viewModel = viewModel,navHostController=navHostController,search=search)
                }
                else if(pagerState.currentPage==1)
                {
                    FavoriteScreen(viewModel = viewModel,search=search)
                }
            }
        }
    }
}
@Composable
fun UserScreen(viewModel: ListViewModel,navHostController:NavHostController,search: String)
{
    val context= LocalContext.current
    Column(modifier = Modifier.fillMaxSize())
    {
        viewModel.state.listOfUsers?.filter { it.name?.contains(search)?:false}?.forEach{
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
@Composable
fun FavoriteScreen(viewModel: ListViewModel,search: String)
{
    Column(modifier = Modifier.fillMaxSize())
    {
        viewModel.state.listOfFavoriteUser?.filter{ it.name?.contains(search)?:false}?.forEach{
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
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(pagerState: PagerState)
{
    val listOfTabs = listOf(
        "User","Favorite"
    )
    val scope= rememberCoroutineScope()
    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = UserTheme.dimens.x_20_dp),
        backgroundColor = Color.White,
        selectedTabIndex = pagerState.currentPage,
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
                                style = MaterialTheme.typography.body1.copy(
                                    fontWeight = FontWeight.Medium
                                )
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