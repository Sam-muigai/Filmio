package com.samkt.filmio.featureMovies.presentation.savedFilms

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.samkt.filmio.featureMovies.data.local.entities.MovieEntity
import com.samkt.filmio.featureMovies.data.local.entities.TvSeriesEntity
import com.samkt.filmio.featureMovies.presentation.sharedComponents.MovieItem

@Composable
fun WatchListScreen(
    viewModel: SavedFilmsViewModel = hiltViewModel()
) {
    val movies = viewModel.savedMovies.collectAsState().value
    val tvSeries = viewModel.savedTvSeries.collectAsState().value
    WatchListScreenContent(
        movies = movies,
        tvSeries = tvSeries
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WatchListScreenContent(
    modifier: Modifier = Modifier,
    movies: List<MovieEntity>,
    tvSeries: List<TvSeriesEntity>
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "WatchList",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn(
                content = {
                    if (movies.isNotEmpty()) {
                        stickyHeader {
                            Row(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp, horizontal = 20.dp)
                            ) {
                                Text(
                                    text = "Movies",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    items(movies) { movie ->
                        MovieItem(
                            filmName = movie.title ?: movie.originalTitle ?: "",
                            filmOverview = movie.overview ?: "",
                            imageUrl = movie.posterPath ?: ""
                        )
                    }
                    if (tvSeries.isNotEmpty()) {
                        stickyHeader {
                            Row(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp, horizontal = 20.dp)
                            ) {
                                Text(
                                    text = "TvSeries",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    items(tvSeries) { tvSeries ->
                        MovieItem(
                            filmName = tvSeries.name ?: tvSeries.originalName ?: "",
                            filmOverview = tvSeries.overview ?: "",
                            imageUrl = tvSeries.posterPath ?: ""
                        )
                    }
                }
            )
        }
    }
}
