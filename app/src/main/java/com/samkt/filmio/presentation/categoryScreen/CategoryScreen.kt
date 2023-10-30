package com.samkt.filmio.presentation.categoryScreen

import androidx.compose.foundation.clickable
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
import com.samkt.filmio.data.dtos.Movie
import com.samkt.filmio.data.dtos.TVSeries
import com.samkt.filmio.presentation.sharedComponents.MoviesLazyGrid
import com.samkt.filmio.presentation.sharedComponents.TvSeriesLazyGrid

@Composable
fun CategoryScreen(
    category: String?,
    viewModel: CategoryScreenViewModel = hiltViewModel(),
    onBackClicked: () -> Unit = {},
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit,
    onTvSeriesClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val tvSeries = viewModel.tvSeries.collectAsLazyPagingItems()

    val isTvSeriesLoading = tvSeries.loadState.refresh is LoadState.Loading
    val isMoviesLoading = movies.loadState.refresh is LoadState.Loading

    val isTvSeriesError = tvSeries.loadState.refresh is LoadState.Error
    val isMoviesError = movies.loadState.refresh is LoadState.Error

    val isLoading = isMoviesLoading && isTvSeriesLoading
    val isError = isTvSeriesError && isMoviesError

    CategoryScreenContent(
        movies = movies,
        isLoading = isLoading,
        isError = isError,
        category = category ?: "Popular",
        onMovieClicked = onMovieClicked,
        onBackClicked = onBackClicked,
        isMovie = viewModel.isMovie,
        setIsMovie = viewModel::setIsMovie,
        tvSeries = tvSeries,
        onTvSeriesClicked = onTvSeriesClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreenContent(
    movies: LazyPagingItems<Movie>,
    tvSeries: LazyPagingItems<TVSeries>,
    isLoading: Boolean,
    category: String,
    isError: Boolean,
    onBackClicked: () -> Unit,
    setIsMovie: (Boolean) -> Unit,
    isMovie: Boolean,
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit,
    onTvSeriesClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
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
                        text = category,
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
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search"
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .clickable {
                                setIsMovie(true)
                            }
                            .padding(8.dp),
                        text = "Movies",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = if (isMovie) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        modifier = Modifier
                            .clickable {
                                setIsMovie(false)
                            }
                            .padding(8.dp),
                        text = "TvSeries",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = if (!isMovie) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    )
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
                if (isMovie) {
                    MoviesLazyGrid(
                        movies = movies,
                        onMovieClicked = onMovieClicked
                    )
                } else {
                    TvSeriesLazyGrid(
                        tvSeries = tvSeries,
                        onTvSeriesClicked = onTvSeriesClicked
                    )
                }
            }
        }
    }
}
