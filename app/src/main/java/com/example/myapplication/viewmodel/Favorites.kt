package com.example.myapplication.viewmodel


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.api.Data
import navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pantalla3(navigationController: NavHostController, myViewModel: APIViewModel) {
    Scaffold(
        topBar = { My3TopAppBar(navigationController) },
        bottomBar = { My3BottomBar(navigationController) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = Color(14, 17, 31, 255))
            ) {

                MyRecyclerView2(
                    myViewModel = myViewModel,
                    navigationController = navigationController
                )
            }
        }
    )
}


@Composable
fun MyRecyclerView2(navigationController: NavHostController, myViewModel: APIViewModel) {
    val showLoading: Boolean by myViewModel.loading.observeAsState(true)
    val favorites: MutableList<Data> by myViewModel.favourites.observeAsState(mutableListOf())
    myViewModel.getFavourites()
    if (showLoading) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary
        )
    } else {
        LazyColumn() {
            items(favorites.size) {
                MapsItem(favorites[it], it, navigationController, myViewModel)
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun My3TopAppBar(navigationController: NavHostController) {
    val font = FontFamily(Font(R.font.valorant))
    TopAppBar(
        title = { Text(text = "FaVourites List", fontFamily = font) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(222, 48, 79),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(onClick = {
                navigationController.navigateUp()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Menu")
            }
        }
    )
}


@Composable
fun My3BottomBar(navigationController: NavHostController) {
    val font = FontFamily(Font(R.font.valorant))
    BottomNavigation(
        backgroundColor = Color(222, 48, 79),
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "home", tint = Color.White) },
            label = { Text("HoMe", fontFamily = font, color = Color.White) },
            selected = true,
            onClick = { navigationController.navigate(Routes.Pantalla1.route) },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.White
        )
    }
}


