package com.example.retrofittesting.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.PointerIconCompat
import com.example.retrofittesting.MovieList
import com.example.retrofittesting.R
import com.example.retrofittesting.model.Movie
import com.example.retrofittesting.viewModel.MovieViewModel

@Composable
fun NetworkStatus(movieViewModel: MovieViewModel, movieList: List<Movie>) {
    val connectivityManager =
        LocalContext.current.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val isOnline = remember { mutableStateOf(false) }
    val networkCallback = remember {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isOnline.value = true
            }

            override fun onLost(network: Network) {
                isOnline.value = false
            }
        }
    }

    DisposableEffect(Unit) {
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            networkCallback
        )
        onDispose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    if (isOnline.value) {
        LaunchedEffect(isOnline.value) {
            // Обновляем страницу
            movieViewModel.getMovieList()
        }
        MovieList(movieList = movieViewModel.movieListResponse)
    } else {
        ShowingConnectionState()
    }
}


@Composable
fun ShowingConnectionState() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_wifi_off_24),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.LightGray),
           modifier = Modifier.size(50.dp)
        )
        Text(
            text = "Internet Disconnected",
            color = Color.LightGray,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp
        )
    }
}


