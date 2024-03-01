package com.example.myapplication.viewmodel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.myapplication.R
import com.example.myapplication.api.Data
import com.example.myapplication.api.Mapa
import navigation.Routes


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Detail(apiViewModel: APIViewModel, navigationController: NavHostController) {
    apiViewModel.getMap(apiViewModel.getIdX())
    val mapa by apiViewModel.map.observeAsState()
    Scaffold(
        topBar = { My2TopAppBar(navigationController, apiViewModel, mapa)}
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .background(color = Color(14, 17, 31, 255))
        ) {
            if (mapa != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    GlideImage(
                        model = mapa?.data?.splash,
                        contentDescription = "Map Image",
                        contentScale = ContentScale.FillHeight,
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(270.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.white),
                                contentDescription = "whiteBackground",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .alpha(0.4f)
                            )
                            GlideImage(
                                model = mapa?.data?.displayIcon,
                                contentDescription = "Character Image",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(270.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(13.dp))
                        mapa?.data?.displayName?.let {
                            Text(
                                textAlign = TextAlign.Center,
                                text = it,
                                fontFamily = apiViewModel.font,
                                color = Color.White,
                                style = TextStyle.Default.copy(
                                    drawStyle = Stroke(
                                        miter = 20f,
                                        width = 8f,
                                        join = StrokeJoin.Round
                                    )
                                ),
                                fontSize = 50.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(modifier = Modifier.height(40.dp))

                        mapa?.data?.narrativeDescription?.let {
                            Text(
                                text = it,
                                color = Color.Black,
                                fontFamily = apiViewModel.font,
                                fontSize = 18.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .background(Color.White.copy(alpha = 0.4f))
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Spacer(modifier = Modifier.height(25.dp))

                        Column(
                            modifier = Modifier.width(200.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            mapa?.data?.coordinates?.let {
                                Text(
                                    text = it,
                                    color = Color.Black,
                                    fontFamily = apiViewModel.font,
                                    fontSize = 18.sp,
                                    lineHeight = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .background(Color.White.copy(alpha = 0.4f))
                                )
                            }
                            Spacer(modifier = Modifier.height(25.dp))

                            mapa?.data?.tacticalDescription?.let {
                                Text(
                                    text = it,
                                    color = Color.Black,
                                    fontFamily = apiViewModel.font,
                                    fontSize = 18.sp,
                                    lineHeight = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .background(Color.White.copy(alpha = 0.4f))
                                )
                            }
                        }

                    }
                }
            } else CircularProgressIndicator(modifier = Modifier.size(32.dp))
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun My2TopAppBar(navigationController: NavHostController, apiViewModel: APIViewModel, mapa:Mapa?) {
    val font = FontFamily(Font(R.font.valorant))
    val isFav by apiViewModel.isFavourite.observeAsState(false)
    if (mapa != null) {
        val map: Data = Data(
            mapa!!.data.coordinates,
            mapa!!.data.displayIcon,
            mapa!!.data.listViewIcon,
            mapa!!.data.displayName,
            mapa!!.data.narrativeDescription,
            mapa!!.data.splash,
            mapa!!.data.tacticalDescription,
            mapa!!.data.uuid
        )
        apiViewModel.isFavorite(map)
        TopAppBar(
            title = { Text(text = "MAps List", fontFamily = font) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(222, 48, 79),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            ),
            navigationIcon = {
                IconButton(onClick = { navigationController.navigate(Routes.Pantalla1.route) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    apiViewModel.changeFavourite()
                    if (isFav) {
                        apiViewModel.saveAsFavourite(map)
                    } else apiViewModel.deleteFavourite(map)

                }) {
                    Icon(
                        imageVector = if (isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favourite"
                    )
                }
            }
        )
    } else CircularProgressIndicator(modifier = Modifier.size(32.dp))
}






