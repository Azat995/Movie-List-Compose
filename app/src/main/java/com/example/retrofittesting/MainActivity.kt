package com.example.retrofittesting

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.retrofittesting.model.Movie
import com.example.retrofittesting.ui.theme.RetrofitTestingTheme
import com.example.retrofittesting.view.MovieItem
import com.example.retrofittesting.view.NetworkStatus
import com.example.retrofittesting.viewModel.MovieViewModel

class MainActivity : ComponentActivity() {

    private val movieViewModel by viewModels<MovieViewModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitTestingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NetworkStatus(
                        movieList = movieViewModel.movieListResponse,
                        movieViewModel = movieViewModel
                    )
                    movieViewModel.getMovieList()
                }

            }
        }
    }
}


@Composable
fun MovieList(movieList: List<Movie>) {
    LazyColumn {
        itemsIndexed(items = movieList) { index, item ->
            MovieItem(movie = item)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrofitTestingTheme {
        val movie =
            Movie("Developer's Says", "", "Hi Guys Please Like And Subscribe to My Chanel", "Hello")
        MovieItem(movie = movie)
    }
}