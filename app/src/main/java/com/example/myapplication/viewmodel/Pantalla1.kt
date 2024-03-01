package com.example.myapplication.viewmodel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.myapplication.R
import com.example.myapplication.api.Data
import com.example.myapplication.api.ValorantMaps
import navigation.Routes



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pantalla1(navigationController: NavHostController, myViewModel: APIViewModel){
    Scaffold(
        topBar = { MyTopAppBar(myViewModel) },
        bottomBar = { MyBottomBar(navigationController)},
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color(14, 17, 31, 255))
            ) {

                MyRecyclerView(myViewModel = myViewModel,
                    navigationController = navigationController
                )
            }
        }
    )
}



val imgMaps = listOf(R.drawable.ascent, R.drawable.split, R.drawable.fracture, R.drawable.bind, R.drawable.breeze, R.drawable.district, R.drawable.kasbah, R.drawable.drift, R.drawable.piazza, R.drawable.lotus, R.drawable.sunset, R.drawable.pearl, R.drawable.icebox, R.drawable.therange, R.drawable.heaven)

@Composable
fun MyRecyclerView(navigationController: NavHostController, myViewModel: APIViewModel){
    val showLoading: Boolean by myViewModel.loading.observeAsState(true)
    val maps: ValorantMaps by myViewModel.maps.observeAsState(ValorantMaps(emptyList(), 0))
    myViewModel.getMaps()
    if (showLoading) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary
        )
    } else {
        LazyColumn() {
            items(maps.data.size) {
                MapsItem(maps.data[it], it, navigationController, myViewModel)
            }

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MapsItem(data: Data, it:Int, navigationController: NavHostController, myViewModel: APIViewModel) {
    val back = data.listViewIcon
    Card(
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                myViewModel.setIdX(data.uuid)
                navigationController.navigate(Routes.Detail.route)
            }
    ) {
        if (data.displayName != "The Range" && data.displayName != "Drift") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                GlideImage(
                    model = back,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Character Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)

                )

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.white),
                            contentDescription = "whiteBackground",
                            modifier = Modifier
                                .size(100.dp)
                                .alpha(0.7f)
                        )

                        GlideImage(
                            model = data.displayIcon,
                            contentDescription = "Character Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(133.dp))

                    Text(
                        text = data.displayName,
                        fontFamily = myViewModel.font,
                        color = Color.White,
                        style = TextStyle.Default.copy(
                            drawStyle = Stroke(
                                miter = 10f,
                                width = 4f,
                                join = StrokeJoin.Round
                            )
                        ),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(myViewModel: APIViewModel) {
    val font = FontFamily(Font(R.font.valorant))
    TopAppBar(
        title = { Text(text = "MAps List", fontFamily = font) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(222,48,79),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            MySearchBar(myViewModel)
        }
    )
}

@Composable
fun MyBottomBar(navigationController: NavHostController) {
    val font = FontFamily(Font(R.font.valorant))
    BottomNavigation(
        backgroundColor = Color(222,48,79),
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.White) },
            label = { Text("FaVourites", fontFamily = font, color = Color.White) },
            selected = true,
            onClick = { navigationController.navigate(Routes.Favourites.route) },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar (myViewModel: APIViewModel) {
    val searchText by myViewModel.searchText.observeAsState("")
    SearchBar(
        query = searchText,
        onQueryChange = { myViewModel.onSearchTextChange(it) },
        onSearch = { myViewModel.onSearchTextChange(it) },
        active = true,
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search", tint = Color.White)},
        placeholder = { Text("What are you looking for?", color = Color.White) },
        onActiveChange = {}, modifier = Modifier
            .fillMaxHeight(0.1f)
            .clip(CircleShape),
        colors = SearchBarDefaults.colors(
            containerColor = Color(222,48,79),
            inputFieldColors = TextFieldDefaults.textFieldColors(
                focusedTextColor = Color.White,
                cursorColor = Color.White
            )
        )
        ) {
    }
}


