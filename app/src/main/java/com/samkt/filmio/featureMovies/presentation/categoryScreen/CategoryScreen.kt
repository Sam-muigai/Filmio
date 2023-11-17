package com.samkt.filmio.featureMovies.presentation.categoryScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.samkt.filmio.featureMovies.data.remote.dtos.Movie
import com.samkt.filmio.featureMovies.presentation.sharedComponents.MoviesLazyGrid


@Composable
fun CategoryScreen(
    category: String?,
    viewModel: CategoryScreenViewModel = hiltViewModel(),
    onBackClicked: () -> Unit = {},
    onSearchClicked: () -> Unit,
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()

    val isMoviesLoading = movies.loadState.refresh is LoadState.Loading

    val isMoviesError = movies.loadState.refresh is LoadState.Error

    CategoryScreenContent(
        movies = movies,
        isLoading = isMoviesLoading,
        isError = isMoviesError,
        category = category ?: "Popular",
        onMovieClicked = onMovieClicked,
        onSearchClicked = onSearchClicked,
        onBackClicked = onBackClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreenContent(
    movies: LazyPagingItems<Movie>,
    isLoading: Boolean,
    category: String,
    isError: Boolean,
    onBackClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "$category Movies",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 12.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = onSearchClicked) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = "Search"
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        if (!isLoading && !isError) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                MoviesLazyGrid(
                    movies = movies,
                    onMovieClicked = onMovieClicked
                )
            }
        }
    }
}
