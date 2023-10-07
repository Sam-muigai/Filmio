package com.samkt.filmio.presentation.movieScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.samkt.filmio.R
import com.samkt.filmio.data.dtos.Result
import com.samkt.filmio.presentation.homeScreen.HomeScreenViewModel
import com.samkt.filmio.presentation.homeScreen.components.MovieCard
import com.samkt.filmio.presentation.movieScreen.components.MovieTopSection

@Composable
fun MoviesScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {
    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
    val trendingMovies = viewModel.trendingMovies.collectAsLazyPagingItems()
    val upComingMovies = viewModel.upcomingMovies.collectAsLazyPagingItems()

    val isPopularMoviesLoading = popularMovies.loadState.refresh is LoadState.Loading
    val isTrendingMoviesLoading = trendingMovies.loadState.refresh is LoadState.Loading
    val isUpcomingMoviesLoading = upComingMovies.loadState.refresh is LoadState.Loading

    val isLoading = isPopularMoviesLoading || isTrendingMoviesLoading || isUpcomingMoviesLoading

    var category by rememberSaveable {
        mutableStateOf("trending")
    }
    val movies = when (category) {
        "trending" -> trendingMovies
        "popular" -> popularMovies
        "upcoming" -> upComingMovies
        else -> null
    }
    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
            MovieScreenContent(
                movies = movies!!,
                category = category,
                onSearchClicked = {
                    // TODO: Navigate to searchScreen
                },
                onCategoryClicked = {
                    category = it
                }
            )
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreenContent(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Result>,
    category: String = "",
    onSearchClicked: () -> Unit,
    onCategoryClicked: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MovieTopSection(
                onSearchClicked = onSearchClicked,
                category = category,
                onPopularClicked = {
                    onCategoryClicked("popular")
                },
                onTrendingClicked = {
                    onCategoryClicked("trending")
                },
                onUpcomingClicked = {
                    onCategoryClicked("upcoming")
                }
            )
        }
    ) { paddingValues ->
        MoviesLazyGrid(
            modifier = Modifier.padding(paddingValues),
            movies = movies
        )
    }
}

@Composable
fun MoviesLazyGrid(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Result>
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        content = {
            items(
                count = movies.itemCount,
                key = { index -> index }
            ) { movieIndex ->
                val movieUrl = movies[movieIndex]?.posterPath ?: ""
                val imageUrl = "https://image.tmdb.org/t/p/w500/$movieUrl"
                MovieCard(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .height(200.dp),
                    imageUrl = imageUrl,
                    cornerSize = 4.dp
                )
            }
        }
    )
}

